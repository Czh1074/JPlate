package com.plate.msgService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plate.commonutils.R;
import com.plate.commonutils.StringSearchSingleton;
import com.plate.msgService.client.FavClient;
import com.plate.msgService.client.UserClient;
import com.plate.msgService.entity.Message;
import com.plate.msgService.entity.MessageFavoriteItem;
import com.plate.msgService.entity.vo.MessageQuery;
import com.plate.msgService.service.MessageService;
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
@RequestMapping("/msgService/message")
public class MessageController {
    //把service注入
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private FavClient favClient;

    //查询所有兼职信息操作
    //rest风格
    @PostMapping("findAll")
    public R findAllMsg(){
        //调用service的方法来实现所有操作
        val list = messageService.list(null);
        return R.ok().data("items",list);
    }

    //分页查询兼职信息方法
    @PostMapping("pageMessage/{current}/{limit}")
    public R pageListMsg(@PathVariable long current, @PathVariable long limit){

        //创建page对象
        Page<Message> pageMessage = new Page<>(current,limit);

        //调用方法的时候底层封装，把分页的所有数据封装到pageGood对象里面
        messageService.page(pageMessage,null);

        long total = pageMessage.getTotal(); //记录总记录数
        List<Message> records = pageMessage.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    @PostMapping("pageMessageCondition/{current}/{limit}")
    public R pageMessageCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) MessageQuery messageQuery){

        //创建一个page对象
        Page<Message> pageMessage = new Page<>(current,limit);

        QueryWrapper<Message> wrapper = new QueryWrapper<>();

        //多条件组合查询，动态sql
        //判断条件值是否为空，如不为空则拼接
        String name = messageQuery.getName();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        int low = messageQuery.getLow();
        if(low >0 ){
            wrapper.gt("salary",low);
        }

        //调用方法实现条件查询分页
        messageService.page(pageMessage,wrapper);

        long total = pageMessage.getTotal(); //记录总记录数
        List<Message> records = pageMessage.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map).data("total",total).data("rows",records);
    }


    //删除兼职接口
    @DeleteMapping("{id}")
    public R delMessageById(@PathVariable String id){

        boolean flag = messageService.removeById(id);
        //删除的时候要注意把favorite的数据也都删除
        //1.查询到messageId为id的favorite，然后删除
        favClient.delFavoriteByInfoId(id,3);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //---------------------------------------------前台
    //条件查询兼职信息带分页的方法
    @PostMapping("pageMessageFrontList/{current}/{limit}/{exchange}")
    public R pageMessageFrontList(@PathVariable long current, @PathVariable long limit,
                                  @PathVariable int exchange){

        //创建一个page对象
        Page<Message> pageMessage = new Page<>(current, limit);

        Map<String,Object> map = messageService.getMessageFrontList(pageMessage,exchange);

        return R.ok().data(map);
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
            Message message = messageService.getById(infoId);
            message.setCollectCount(message.getCollectCount()+1);

            messageService.updateById(message);

            Map<String, Object> data = r.getData();
            System.out.println("data==================="+data);
            String favoriteId1 = (String) data.get("favoriteId");
            return R.ok().data("id",favoriteId1);
        }

        if(collect.equals("false")){
            //通过favoriteId删除收藏
            Message message = messageService.getById(infoId);
            message.setCollectCount(message.getCollectCount()-1);
            messageService.updateById(message);
            favClient.delFavorite(favoriteId);
            return R.ok();
        }

        return R.error();
    }



    //查询兼职详细信息--------------------------------------------------------------------跨服务调用实现是否收藏的判断
    @PostMapping("getMessageInfoById/{infoId}/{userId}")
    public R getMessageInfoById(@PathVariable String infoId,@PathVariable String userId){

        R favorite = favClient.getFavorite(userId, infoId);

        Map<String, Object> favoriteData = favorite.getData();

        Message message = messageService.getById(infoId);

        //获得userId2，然后返回相应的该动态发布者信息给前端展示头像和昵称
        String userId2 = message.getUserId();
        R rUser = userClient.getUser(userId2);
        //把rUser--R转化为User类型
        Object user = rUser.getData().get("user");

        System.out.println("favorite"+favoriteData);
        if(favorite.getSuccess()){
            System.out.println("查找成功:          11111     " + favorite);
            message.setCollect(true);
        }else{
            System.out.println("查找无效");
            message.setCollect(false);
        }

        message.setViewCount(message.getViewCount()+1);
        messageService.updateById(message);


        return R.ok().data("message",message).data("favorite",favoriteData).data("user",user);
    }

        //添加兼职接口
        @PostMapping("addMessage")
        public R addMessage(@RequestBody Message message){

            System.out.println(message);
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            message.setId(uuid);
            message.setGmtCreate(new Date());
            message.setGmtModified(new Date());
            //过滤
            String name = StringSearchSingleton.instance().filterSensitivityWord(message.getName(), '*');
            String introduction = StringSearchSingleton.instance().filterSensitivityWord(message.getIntroduction(), '*');
            String address = StringSearchSingleton.instance().filterSensitivityWord(message.getAddress(), '*');
            message.setName(name);
            message.setIntroduction(introduction);
            message.setAddress(address);

            boolean save = messageService.save(message);
            if (save){
                return R.ok();
            }else {
                return R.error();
            }
        }


        //----------------------跨服务调用
        @PostMapping("selectMessage/{userId}/{infoType}")
        public R selectMessage(@PathVariable String userId,@PathVariable Integer infoType){

        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("userId",userId);
        queryWrapper.eq("info_Type",infoType);

        List<Message> list = messageService.list(queryWrapper);

        return R.ok().data("messageList",list);

        }

        //跨服务调用----------------调用通过id查找兼职信息
        @PostMapping("getMessageById/{id}")
        public R getMessageById(@PathVariable("id") String id){
            Message message = messageService.getById(id);
            //获得该兼职信息的发布者头像和昵称
            R user = userClient.getUser(message.getUserId());
            MessageFavoriteItem messageFavoriteItem = new MessageFavoriteItem();
            messageFavoriteItem.setMessage(message);
            Map<String, Object> data = (Map<String, Object>) user.getData().get("user");
            messageFavoriteItem.setSalt((String) data.get("salt"));
            messageFavoriteItem.setNickName((String) data.get("nickName"));
            return R.ok().data("messageFavoriteItem",messageFavoriteItem);
        }

    //发布中心实现
    //跨服务调用----------------调用通过id查找兼职信息
    @PostMapping("getMessagePageList/{current}/{limit}/{userId}")
    public R getMessagesByUserId(@PathVariable long current, @PathVariable long limit,
                               @PathVariable("userId") String userId){

        //通过userId获取到messages
        List<Message> messages = messageService.selectMessagesByUserId(userId);
        return R.ok().data("messages",messages);
    }
}

