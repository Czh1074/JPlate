package com.plate.userService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plate.commonutils.JwtUtils;
import com.plate.commonutils.R;
import com.plate.servicebase.exceptionhandler.PlateException;
import com.plate.userService.entity.User;
import com.plate.userService.entity.vo.RegisterVo;
import com.plate.userService.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czh
 * @since 2022-04-28
 */
@RestController
@RequestMapping("/userService/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login(@RequestBody User user) {
        //调用service实现登录
        //返回token的值，使用jwt生成
        String token = userService.login(user);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        userService.register(registerVo);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R delUserById(@PathVariable String id){

        boolean flag = userService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @PostMapping("getUserInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
            String userId = JwtUtils.getMemberIdByJwtToken(request);
            User userInfo = userService.getById(userId);
            return R.ok().data("item", userInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new PlateException(20001,"error");
        }
    }

    //通过用户id获取用户信息
    @PostMapping("getUser/{id}")
    public R getUser(@PathVariable String id){
        User user = userService.getById(id);
        return R.ok().data("user",user);
    }

    //修改用户信息
    @PostMapping("updateUser")
    public R getUser(@RequestBody User user){

        userService.updateById(user);
        //修改token
        String token = userService.updateToken(user);
        return R.ok().data("token", token);
    }


    //邮箱发送验证码
    @PostMapping("/sendSimpleMail/{emailReceiver}")
    public R sendSimpleMail(@PathVariable String emailReceiver) {
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件发送者
        message.setFrom("2425540101@qq.com");
        // 设置邮件接收者
        message.setTo(emailReceiver);
        // 设置邮件的主题
        message.setSubject("注册验证码");
        // 设置邮件的正文
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int r = random.nextInt(10);
            code.append(r);
        }
        String text = "您的验证码为：" + code + ",请勿泄露给他人。";
        message.setText(text);

        // 发送邮件
        try {
            javaMailSender.send(message);
            //将验证码保存到redis缓存中，设置有效时间为5分钟
            redisTemplate.opsForValue().set(emailReceiver, String.valueOf(code),5, TimeUnit.MINUTES);
            return R.ok();
        } catch (MailException e) {
            e.printStackTrace();
        }
        return R.error().message("发送失败");
    }

    //条件查询带分页的方法
    @PostMapping("pageUserCondition/{current}/{limit}/{username}")
    public R pageUserCondition(@PathVariable long current, @PathVariable long limit,
                                @PathVariable String username){

        //创建一个page对象
        Page<User> pageTrend = new Page<>(current,limit);

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //多条件组合查询，动态sql
        //判断条件值是否为空，如不为空则拼接

        if(!StringUtils.isEmpty(username)){
            wrapper.like("username",username);
//            wrapper.eq("username",username);
        }

        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        userService.page(pageTrend,wrapper);

        long total = pageTrend.getTotal(); //记录总记录数
        List<User> records = pageTrend.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map).data("total",total).data("rows",records);
    }
}

