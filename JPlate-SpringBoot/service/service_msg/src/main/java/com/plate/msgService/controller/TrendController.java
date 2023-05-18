package com.plate.msgService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plate.commonutils.R;
import com.plate.commonutils.StringSearchSingleton;
import com.plate.msgService.client.FavClient;
import com.plate.msgService.client.UserClient;
import com.plate.msgService.entity.Comment;
import com.plate.msgService.entity.Trend;
import com.plate.msgService.entity.TrendFavoriteItem;
import com.plate.msgService.entity.vo.TrendQuery;
import com.plate.msgService.service.CommentService;
import com.plate.msgService.service.TrendService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czh
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/trendService/trend")
public class TrendController {
    //把service注入
    @Autowired
    private TrendService trendService;

    @Autowired
    private CommentService commentService;

    //跨服务调用注入
    @Autowired
    private UserClient userClient;

    @Autowired
    private FavClient favClient;

    //查询前八条热门课程
    @PostMapping("index")
    public R index(){
        List<Trend> trends = trendService.selectAllBanner();
        return R.ok().data("trends",trends);
    }

    //查询所有动态信息操作
    //rest风格
    @GetMapping("findAll")
    public R findAllTrend(){
        //调用service的方法来实现所有操作
        val list = trendService.list(null);
        return R.ok().data("items",list);
    }

    //分页查询动态信息方法
    @PostMapping("pageTrend/{current}/{limit}")
    public R pageListTrend(@PathVariable long current, @PathVariable long limit){

        //创建page对象
        Page<Trend> pageTrend = new Page<>(current,limit);

        //调用方法的时候底层封装，把分页的所有数据封装到pageTrend对象里面
        trendService.page(pageTrend,null);

        long total = pageTrend.getTotal(); //记录总记录数
        List<Trend> records = pageTrend.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    @PostMapping("pageTrendCondition/{current}/{limit}")
    public R pageTrendCondition(@PathVariable long current, @PathVariable long limit,
                               @RequestBody(required = false) TrendQuery trendQuery){

        //创建一个page对象
        Page<Trend> pageTrend = new Page<>(current,limit);

        QueryWrapper<Trend> wrapper = new QueryWrapper<>();

        //多条件组合查询，动态sql
        //判断条件值是否为空，如不为空则拼接
        String project = trendQuery.getProject();

        if(!StringUtils.isEmpty(project)){
            wrapper.like("project",project);
        }

        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        trendService.page(pageTrend,wrapper);

        long total = pageTrend.getTotal(); //记录总记录数
        List<Trend> records = pageTrend.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map).data("total",total).data("rows",records);
    }


    //删除动态接口
    @DeleteMapping("{id}")
    public R delTrendById(@PathVariable String id){

        boolean flag = trendService.removeById(id);
        //删除的时候要注意把favorite的数据也都删除
        //1.查询到messageId为id的favorite，然后删除
        favClient.delFavoriteByInfoId(id,1);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //-----------------------------------------------------------动态前台
    //条件查询动态信息带分页的方法
    @PostMapping("pageTrendFrontList/{current}/{limit}/{exchange}")
    public R pageTrendFrontList(@PathVariable long current, @PathVariable long limit,
                                @PathVariable int exchange){
        //创建一个page对象
        Page<Trend> pageTrend = new Page<>(current, limit);
        Map<String,Object> map = trendService.getTrendFrontList(pageTrend,exchange);
        //返回热度最高的六条
        List<Trend> list = trendService.getHotTrendFrontList();
        return R.ok().data(map).data("hotTrends",list);
    }

    //查询动态详细信息--------------------------------------------------------------------跨服务调用实现是否收藏的判断
    @PostMapping("getTrendInfoById/{infoId}/{userId}")
    public R getTrendInfoById(@PathVariable String infoId,@PathVariable String userId){

        R favorite = favClient.getFavorite(userId, infoId);
        Map<String, Object> favoriteData = favorite.getData();
        Trend trend = trendService.getById(infoId);
        //获得userId2，然后返回相应的该动态发布者信息给前端展示头像和昵称
        String userId2 = trend.getUserId();
        R rUser = userClient.getUser(userId2);
        //把rUser--R转化为User类型
        Object user = rUser.getData().get("user");
        System.out.println("测试user" + user);
        if(favorite.getSuccess()){
            trend.setCollect(true);
        }else{
            trend.setCollect(false);
        }
        trend.setViewCount(trend.getViewCount()+1);
        trendService.updateById(trend);
        return R.ok().data("trend",trend).data("favorite",favoriteData).data("user",user);
    }

    //添加收藏---逻辑，前端传回当前的collect是true还是false
    //1、true的话就进行收藏---需要字段favorite这个实体
    //2、fasle的话就进行删除----需要favoriteId
    @PostMapping("/addFavorite/{favoriteId}/{userId}/{infoId}/{collect}/{infoType}")
    public R updateFavorite(@PathVariable("favoriteId") String favoriteId,@PathVariable("userId") String userId,
                         @PathVariable("infoId") String infoId,@PathVariable("collect") String collect,
                         @PathVariable("infoType") Integer infoType){
//        System.out.println("favoriteId:"+favoriteId+",userId:"+userId+",infoId:"+infoId+",collect:"+collect+",infoType:"+infoType);
        if(collect.equals("true")){
            //跨服务调用---增加收藏功能
            R r = favClient.addFavorite(userId, infoId, infoType);

            //通过InfoType找到对应的表
            Trend trend = trendService.getById(infoId);
            trend.setCollectCount(trend.getCollectCount()+1);

            trendService.updateById(trend);

            Map<String, Object> data = r.getData();
            String favoriteId1 = (String) data.get("favoriteId");
            return R.ok().data("id",favoriteId1);
        }

        if(collect.equals("false")){
            //通过favoriteId删除收藏
            Trend trend = trendService.getById(infoId);
            trend.setCollectCount(trend.getCollectCount()-1);
            trendService.updateById(trend);
            favClient.delFavorite(favoriteId);
            return R.ok();
        }

        return R.error();
    }


    //查询评论列表
    //条件查询动态信息带分页的方法
    @PostMapping("getCommentList/{current}/{limit}/{trendId}")
    public R pageCommentFrontList(@PathVariable long current, @PathVariable long limit,
                                  @PathVariable String trendId){

        //创建一个page对象
        Page<Comment> pageComment = new Page<>(current, limit);

        Map<String,Object> map = commentService.selectCommentByTrendId(pageComment,trendId);

        return R.ok().data(map);

    }


    //添加评论接口
    @PostMapping("addComment")
    public R addComment(@RequestBody Comment comment){

        System.out.println(comment);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        comment.setId(uuid);
        comment.setGmtCreate(new Date());
        comment.setGmtModefied(new Date());

        String msg = StringSearchSingleton.instance().filterSensitivityWord(comment.getMessage(), '*');
        comment.setMessage(msg);
        //对评论数进行修改
        String trendId = comment.getTrendId();

        Trend trend = trendService.getById(trendId);

        trend.setCommentCount(trend.getCommentCount()+1);

        trendService.updateById(trend);

        boolean save = commentService.save(comment);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //添加动态接口
    @PostMapping("addTrend")
    public R addTrend(@RequestBody Trend trend){

        System.out.println(trend);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        trend.setId(uuid);
        trend.setGmtCreate(new Date());
        trend.setGmtModified(new Date());
        //过滤
        String project = StringSearchSingleton.instance().filterSensitivityWord(trend.getProject(), '*');
        String introduction = StringSearchSingleton.instance().filterSensitivityWord(trend.getIntroduction(), '*');
        trend.setProject(project);
        trend.setIntroduction(introduction);

        boolean save = trendService.save(trend);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //----------------------跨服务调用
    @PostMapping("selectTrend/{userId}/{infoType}")
    public R selectTrend(@PathVariable String userId,@PathVariable Integer infoType){

        QueryWrapper<Trend> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("userId",userId);
        queryWrapper.eq("info_Type",infoType);


        List<Trend> list = trendService.list(queryWrapper);

        return R.ok().data("trendList",list);

    }

    //跨服务调用----------------调用通过id查找兼职信息
    @PostMapping("getTrendById/{id}")
    public R getTrendById(@PathVariable("id") String id){
//        Trend trend = trendService.getById(id);
//
//        return R.ok().data("trend",trend);
        Trend trend = trendService.getById(id);
        //获得该兼职信息的发布者头像和昵称
        R user = userClient.getUser(trend.getUserId());
        TrendFavoriteItem trendFavoriteItem = new TrendFavoriteItem();
        trendFavoriteItem.setTrend(trend);
        Map<String, Object> data = (Map<String, Object>) user.getData().get("user");
        trendFavoriteItem.setSalt((String) data.get("salt"));
        trendFavoriteItem.setNickName((String) data.get("nickName"));
        return R.ok().data("trendFavoriteItem",trendFavoriteItem);
    }

    //发布中心实现
    //跨服务调用----------------调用通过id查找兼职信息
    @PostMapping("getTrendPageList/{current}/{limit}/{userId}")
    public R getTrendsByUserId(@PathVariable long current, @PathVariable long limit,
                               @PathVariable("userId") String userId){

        //通过userId获取到trends
        List<Trend> trends = trendService.selectTrendsByUserId(userId);
        return R.ok().data("trends",trends);
    }
}

