package com.plate.commonutils.client;

import com.plate.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * create by chenzhihui on 2022/5/3.
 **/
@FeignClient("service-user")
@Component
public interface UserClient {
    //定义调用方法的路径
    @PostMapping("/userService/user/getFavoriteById/{userId}/{infoId}")
    public R getFavorite(@PathVariable("userId") String userId, @PathVariable("infoId") String infoId);

    //添加收藏
    @PostMapping("/userService/user/addFavorite/{userId}/{infoId}/{infoType}")
    public R addFavorite(@PathVariable("userId") String userId,
                         @PathVariable("infoId") String infoId,
                         @PathVariable("infoType") Integer infoType);

    //删除收藏
    @DeleteMapping("/userService/user/delFavorite/{favoriteId}")
    public R delFavorite(@PathVariable("favoriteId") String favoriteId);

    //获取用户信息
    @PostMapping("/userService/user/getUser/{id}")
    public R getUser(@PathVariable("id") String id);
}
