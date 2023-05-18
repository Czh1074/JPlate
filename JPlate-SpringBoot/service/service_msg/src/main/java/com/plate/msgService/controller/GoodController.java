package com.plate.msgService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plate.commonutils.R;
import com.plate.commonutils.StringSearchSingleton;
import com.plate.msgService.client.FavClient;
import com.plate.msgService.client.UserClient;
import com.plate.msgService.entity.Good;
import com.plate.msgService.entity.GoodFavoriteItem;
import com.plate.msgService.entity.GoodType;
import com.plate.msgService.entity.vo.GoodQuery;
import com.plate.msgService.service.GoodService;
import com.plate.msgService.service.GoodTypeService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czh
 * @since 2022-05-01
 */
@RestController
@RequestMapping("/goodService/good")
public class GoodController {
    //把service注入
    @Autowired
    private GoodService goodService;

    @Autowired
    private GoodTypeService goodTypeService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private FavClient favClient;


    //首页商品信息
    //查询前八条热门商品
    @Cacheable(key = "'goodIndexList'",value = "banner")
    @PostMapping("index")
    public R index(){

        QueryWrapper<Good> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<Good> goods = goodService.list(wrapper);
        return R.ok().data("goods",goods);
    }


    //查询所有公告信息操作
    //rest风格
    @GetMapping("findAll")
    public R findAllGood(){
        //调用service的方法来实现所有操作
        val list = goodService.list(null);
        return R.ok().data("items",list);
    }

    //分页查询商品信息方法
    @PostMapping("pageGood/{current}/{limit}")
    public R pageListGood(@PathVariable long current, @PathVariable long limit){

        //创建page对象
        Page<Good> pageGood = new Page<>(current,limit);

        //调用方法的时候底层封装，把分页的所有数据封装到pageGood对象里面
        goodService.page(pageGood,null);

        long total = pageGood.getTotal(); //记录总记录数
        List<Good> records = pageGood.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    @PostMapping("pageGoodCondition/{current}/{limit}")
    public R pageGoodCondition(@PathVariable long current, @PathVariable long limit,
                               @RequestBody(required = false) GoodQuery goodQuery){

        //创建一个page对象
        Page<Good> pageGood = new Page<>(current,limit);

        QueryWrapper<Good> wrapper = new QueryWrapper<>();

        //多条件组合查询，动态sql
        //判断条件值是否为空，如不为空则拼接
        String sort = goodQuery.getSort();

        if(!StringUtils.isEmpty(sort)){
            wrapper.eq("sort",sort);
        }

        int low = goodQuery.getLow();
        if(low >0 ){
            wrapper.gt("price",low);
        }

        int high = goodQuery.getHigh();
        if(high >0 ){
            wrapper.lt("price",high);
        }



        //调用方法实现条件查询分页
        goodService.page(pageGood,wrapper);

        long total = pageGood.getTotal(); //记录总记录数
        List<Good> records = pageGood.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map).data("total",total).data("rows",records);
    }


    //删除商品接口
    @DeleteMapping("{id}")
    public R delGoodById(@PathVariable String id){

        boolean flag = goodService.removeById(id);
        //删除的时候要注意把favorite的数据也都删除
        //1.查询到messageId为id的favorite，然后删除
        favClient.delFavoriteByInfoId(id,2);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //---------------------------------------------前台


    //条件查询商品信息带分页的方法
    @PostMapping("pageGoodFrontList/{current}/{limit}")
    public R pageGoodFrontList(@PathVariable long current, @PathVariable long limit){

        //创建一个page对象
        Page<Good> pageGood = new Page<>(current, limit);

        Map<String,Object> map = goodService.getGoodFrontList(pageGood);

        return R.ok().data(map);
    }

    //条件(goodQuery)查询商品信息带分页的方法
    @PostMapping("selectGoodBygoodQuery/{current}/{limit}")
    public R selectGoodBySort(@PathVariable long current, @PathVariable long limit,
                              @RequestBody(required = false) GoodQuery goodQuery){

        //创建一个page对象
        Page<Good> pageGood = new Page<>(current, limit);
        Map<String,Object> map = goodService.selectGoodByQuery(pageGood,goodQuery);

        return R.ok().data(map);
    }

//    //查询商品详细信息
//    @GetMapping("getGoodfoById/{id}")
//    public R getNoticeInfoById(@PathVariable String id){
//
//        Good good = goodService.getById(id);
//        good.setViewCount(good.getViewCount()+1);
//        goodService.updateById(good);
//
//        GoodType goodType = goodTypeService.getById(good.getSort());
//        String sortType = goodType.getGoodType();
//        System.out.println("商品分类的类别输出："+sortType);
//        good.setViewCount(good.getViewCount()+1);
//        goodService.updateById(good);
//        return R.ok().data("good",good).data("sortType",sortType);
//    }


    //查询商品详细信息
    @PostMapping("getGoodInfoById/{infoId}/{userId}")
    public R getNoticeInfoById(@PathVariable String infoId,@PathVariable String userId){

        R favorite = favClient.getFavorite(userId, infoId);

        Map<String, Object> favoriteData = favorite.getData();

        Good good = goodService.getById(infoId);
        //获得userId2，然后返回相应的该动态发布者信息给前端展示头像和昵称
        String userId2 = good.getUserId();
        R rUser = userClient.getUser(userId2);
        //把rUser--R转化为User类型
        Object user = rUser.getData().get("user");
        System.out.println("favorite"+favoriteData);
        if(favorite.getSuccess()){
            System.out.println("查找成功:          11111     " + favorite);
            good.setCollect(true);
        }else{
            System.out.println("查找无效");
            good.setCollect(false);
        }

        GoodType goodType = goodTypeService.getById(good.getSort());

        String sortType = goodType.getGoodType();
        System.out.println("商品分类的类别输出："+sortType);
        good.setViewCount(good.getViewCount()+1);
        goodService.updateById(good);
        return R.ok().data("good",good).data("favorite",favoriteData)
                .data("sortType",sortType).data("user",user);
    }

    //添加收藏---逻辑，前端传回当前的collect是true还是false
    //1、true的话就进行收藏---需要字段favorite这个实体
    //2、fasle的话就进行删除----需要favoriteId
    @PostMapping("/addFavorite/{favoriteId}/{userId}/{infoId}/{collect}/{infoType}")
    public R updateFavorite(@PathVariable("favoriteId") String favoriteId,@PathVariable("userId") String userId,
                            @PathVariable("infoId") String infoId,@PathVariable("collect") String collect,
                            @PathVariable("infoType") Integer infoType){
        System.out.println("favoriteId:"+favoriteId+",userId:"+userId+",infoId:"+infoId+",collect:"+collect+",infoType:"+infoType);
        if(collect.equals("true")){
            //跨服务调用---增加收藏功能
            R r = favClient.addFavorite(userId, infoId, infoType);

            //通过InfoType找到对应的表
            Good good = goodService.getById(infoId);
            good.setCollectCount(good.getCollectCount()+1);

            goodService.updateById(good);

            Map<String, Object> data = r.getData();
            System.out.println("data==================="+data);
            String favoriteId1 = (String) data.get("favoriteId");
            return R.ok().data("id",favoriteId1);
        }

        if(collect.equals("false")){
            //通过favoriteId删除收藏
            Good good = goodService.getById(infoId);
            good.setCollectCount(good.getCollectCount()-1);
            goodService.updateById(good);
            favClient.delFavorite(favoriteId);
            return R.ok();
        }

        return R.error();
    }



    //添加商品接口
    @PostMapping("addGood")
    public R addGood(@RequestBody Good good){

        System.out.println(good);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        good.setId(uuid);
        good.setGmtCreate(new Date());
        good.setGmtModified(new Date());
        //过滤
        String name = StringSearchSingleton.instance().filterSensitivityWord(good.getName(), '*');
        String introduction = StringSearchSingleton.instance().filterSensitivityWord(good.getIntroduction(), '*');
        good.setName(name);
        good.setIntroduction(introduction);

        boolean save = goodService.save(good);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //跨服务调用----------------调用通过id查找商品信息
    @PostMapping("getGoodById/{id}")
    public R getGoodById(@PathVariable("id") String id){
        Good good = goodService.getById(id);
        //获得该商品信息的发布者头像和昵称
        R user = userClient.getUser(good.getUserId());
        GoodFavoriteItem goodFavoriteItem = new GoodFavoriteItem();
        goodFavoriteItem.setGood(good);
        Map<String, Object> data = (Map<String, Object>) user.getData().get("user");
        goodFavoriteItem.setSalt((String) data.get("salt"));
        goodFavoriteItem.setNickName((String) data.get("nickName"));
        return R.ok().data("goodFavoriteItem",goodFavoriteItem);
    }

    //发布中心实现
    //跨服务调用----------------调用通过id查找商品信息
    @PostMapping("getGoodPageList/{current}/{limit}/{userId}")
    public R getGoodyUserId(@PathVariable long current, @PathVariable long limit,
                               @PathVariable("userId") String userId){

        //通过userId获取到trends
        List<Good> goods =goodService.selectGoodsByUserId(userId);
        return R.ok().data("goods",goods);
    }

}

