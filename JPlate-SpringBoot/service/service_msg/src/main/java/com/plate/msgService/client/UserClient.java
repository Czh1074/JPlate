package com.plate.msgService.client;

import com.plate.commonutils.R;
import io.swagger.models.auth.In;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * create by chenzhihui on 2022/5/3.
 **/
@FeignClient("service-user")
@Component
public interface UserClient {
    //获取用户信息
    @PostMapping("/userService/user/getUser/{id}")
    public R getUser(@PathVariable("id") String id);
}
