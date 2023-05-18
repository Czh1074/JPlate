package com.plate.msgService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * create by chenzhihui on 2022/4/27.
 **/
@ComponentScan({"com.plate"})
@SpringBootApplication
@MapperScan("com.plate.msgService.mapper")
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
public class MsgApplication {

    public static void main(String[] args){

        SpringApplication.run(MsgApplication.class,args);
    }
}
