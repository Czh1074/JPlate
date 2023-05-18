package com.plate.userService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * create by chenzhihui on 2022/4/28.
 **/

@ComponentScan({"com.plate"})
@SpringBootApplication
@MapperScan("com.plate.userService.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class UserApplication {
    public static void main(String[] args){

        SpringApplication.run(UserApplication.class,args);
    }
}
