package com.plate.userService.service;

import com.plate.userService.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.plate.userService.entity.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czh
 * @since 2022-04-28
 */
public interface UserService extends IService<User> {
    //登录方法
    String login(User user);

    //注册方法
    void register(RegisterVo registerVo);

    String updateToken(User user);
}
