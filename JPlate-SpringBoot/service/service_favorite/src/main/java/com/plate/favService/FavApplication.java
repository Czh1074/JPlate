package com.plate.favService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * create by chenzhihui on 2022/5/26.
 **/
@ComponentScan({"com.plate"})
@SpringBootApplication
@MapperScan("com.plate.favService.mapper")
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
public class FavApplication {
    public static void main(String[] args){
        SpringApplication.run(FavApplication.class,args);
    }
}
