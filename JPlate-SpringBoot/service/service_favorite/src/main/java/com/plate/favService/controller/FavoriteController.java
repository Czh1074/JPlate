package com.plate.favService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plate.commonutils.R;
import com.plate.favService.client.MessageClient;
import com.plate.favService.entity.Favorite;
import com.plate.favService.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czh
 * @since 2022-05-03
 */
@RestController
@RequestMapping("/favService/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private MessageClient messageClient;

    //添加收藏
    @PostMapping("/addFavorite/{userId}/{infoId}/{infoType}")
    public R addFavorite(@PathVariable("userId") String userId,
            @PathVariable("infoId") String infoId,
            @PathVariable("infoType") Integer infoType){
        Favorite favorite = new Favorite();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        favorite.setId(uuid);
        favorite.setUserId(userId);
        favorite.setInfoId(infoId);
        favorite.setInfoType(infoType);
        favorite.setGmtCreate(new Date());
        favoriteService.save(favorite);

        //todo 要实现调用trend的服务，对其collectCount进行修改

        return R.ok().data("favoriteId",uuid);
    }

    //删除收藏
    @DeleteMapping("/delFavorite/{favoriteId}")
    public R delFavorite(@PathVariable("favoriteId") String favoriteId){
        favoriteService.removeById(favoriteId);
        return R.ok();
    }

    //删除掉infoId的所有favorite
    @DeleteMapping("/delFavoriteByInfoId//{infoId}/{infoType}")
    public R delFavoriteByInfoId(@PathVariable("infoId") String infoId,
            @PathVariable("infoType") Integer infoType){
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("info_id",infoId);
        queryWrapper.eq("info_type",infoType);
        favoriteService.remove(queryWrapper);
        return R.ok();
    }

    //通过id获取收藏信息
    //判断当前使用对象是否有收藏该文章
    @PostMapping("/getFavoriteById/{userId}/{infoId}")
    public R getFavorite(@PathVariable("userId") String userId,@PathVariable("infoId") String infoId){
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("info_id",infoId);

        Favorite favorite = favoriteService.getOne(queryWrapper);

        if(favorite != null){
            return R.ok().data("favorite",favorite);
        }else{
            return R.error().data("favorite",favorite);
        }

    }

    //收藏夹实现----分类查询动态、商品、兼职收藏
    @PostMapping("getMessageFavoritePageList/{current}/{limit}/{userId}/{infoType}")
    public R getMessageFavoritePageList(@PathVariable long current,@PathVariable long limit,
    @PathVariable String userId,@PathVariable Integer infoType){

        //1.通过userId和infoType实现查找对应的infoType类型的收藏
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("info_Type",infoType);
        List<Favorite> favorites = favoriteService.list(queryWrapper); //查找到对应的infoId

        if(infoType == 1){//动态
            List<Map<String, Object>> trends = new ArrayList<>();
            for (Favorite favorite : favorites) {
                //2.通过favoriteList找到infoId
                //调用Message的服务实现通过id查找message信息
                R r = messageClient.getTrendById(favorite.getInfoId());
                trends.add((Map<String, Object>) r.getData().get("trendFavoriteItem"));
            }
            return R.ok().data("trends",trends);
        }else if(infoType == 2){//商品
            List<Map<String, Object>> goods = new ArrayList<>();
            for (Favorite favorite : favorites) {
                //2.通过favoriteList找到infoId
                //调用Message的服务实现通过id查找message信息
                R r = messageClient.getGoodById(favorite.getInfoId());
                goods.add((Map<String, Object>) r.getData().get("goodFavoriteItem"));
            }
            return R.ok().data("goods",goods);
        }else{//兼职信息
            List<Map<String, Object>> messages = new ArrayList<>();
            for (Favorite favorite : favorites) {
                //2.通过favoriteList找到infoId
                //调用Message的服务实现通过id查找message信息
                R r = messageClient.getMessageById(favorite.getInfoId());
                messages.add((Map<String, Object>) r.getData().get("messageFavoriteItem"));
            }
            return R.ok().data("messages",messages);
        }
    }

}

