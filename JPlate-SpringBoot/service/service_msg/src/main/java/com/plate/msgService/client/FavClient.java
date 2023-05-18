package com.plate.msgService.client;

import com.plate.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * create by chenzhihui on 2022/5/3.
 **/
@FeignClient("service-fav")
@Component
public interface FavClient {
    //定义调用方法的路径
    @PostMapping("/favService/favorite/getFavoriteById/{userId}/{infoId}")
    public R getFavorite(@PathVariable("userId") String userId, @PathVariable("infoId") String infoId);

    //添加收藏
    @PostMapping("/favService/favorite/addFavorite/{userId}/{infoId}/{infoType}")
    public R addFavorite(@PathVariable("userId") String userId,
                         @PathVariable("infoId") String infoId,
                         @PathVariable("infoType") Integer infoType);

    //删除收藏
    @DeleteMapping("/favService/favorite/delFavorite/{favoriteId}")
    public R delFavorite(@PathVariable("favoriteId") String favoriteId);

    //删除掉infoId的所有favorite
    @DeleteMapping("/favService/favorite/delFavoriteByInfoId/{infoId}/{infoType}")
    public R delFavoriteByInfoId(@PathVariable("infoId") String infoId, @PathVariable("infoType") Integer infoType);
}
