package com.plate.userService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plate.commonutils.JwtUtils;
import com.plate.commonutils.MD5;
import com.plate.commonutils.R;
import com.plate.servicebase.exceptionhandler.PlateException;
import com.plate.userService.entity.User;
import com.plate.userService.entity.vo.RegisterVo;
import com.plate.userService.mapper.UserMapper;
import com.plate.userService.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czh
 * @since 2022-04-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    //登录方法
    @Override
    public String login(User user) {
        //获取登录的邮箱和密码
        String username = user.getUsername();
        String password = user.getPassword();

        //邮箱和密码非空的判断
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new PlateException(20001,"登录失败");
        }

        //判断用户名（邮箱）是否正确
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User usernameMember = baseMapper.selectOne(wrapper);

        //判断查出来的对象是否为空
        if(usernameMember == null){
            //没有这个对象
            throw new PlateException(20001,"登录失败");
        }

        //判断密码对不对
        //因为存储数据库的密码是加密之后的，所以要对获取的密码进行处理，再进行比对
        //MD5
        if(!MD5.encrypt(password).equals(usernameMember.getPassword())){
            throw new PlateException(20001,"登录失败");
        }

        //判断用户是否被禁用
        if(usernameMember.getDeleted()){
            throw new PlateException(20001,"登录失败");
        }

        //登录成功：使用token的工具类返回token字符串
        String jwtToken = JwtUtils.getJwtToken(usernameMember.getId(), usernameMember.getNickName());//记得使用emailMember，不然member只包含两个属性
        return jwtToken;
    }

    //更改token


    @Override
    public void register(RegisterVo registerVo) {

        //获取注册的数据
        String code = registerVo.getCode();//验证码
        String username = registerVo.getUsername();//邮箱
        String nickname = registerVo.getNickName();//昵称
        String password = registerVo.getPassword();//密码

        System.out.println("1:"+ code);
        System.out.println("2" + username);
        System.out.println("3" + nickname);
        System.out.println("4" + password);

        //非空的判断
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)
                ||StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new PlateException(20001,"注册失败");
        }

        //判断验证码
        String redisCode = (String) redisTemplate.opsForValue().get(username);
        if(!code.equals(redisCode)){
            throw new PlateException(20001,"注册失败");
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        //判断邮箱是否重复，表里存在相同手机号就不进行添加
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new PlateException(20001,"注册失败");
        }

        //数据添加到数据库中
        User user = new User();
        user.setId(uuid);
        user.setUsername(username);
        user.setNickName(nickname);
        user.setPassword(MD5.encrypt(password));//进行MD5加密存储
        user.setDeleted(false);//用户不禁用
        user.setSalt("https://jmu-plate.oss-cn-beijing.aliyuncs.com/2022/04/26/1.jpg");//设置默认的头像
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        baseMapper.insert(user);
    }

    @Override
    public String updateToken(User user) {
        String jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickName());//记得使用emailMember，不然member只包含两个属性
        return  jwtToken;
    }




}
