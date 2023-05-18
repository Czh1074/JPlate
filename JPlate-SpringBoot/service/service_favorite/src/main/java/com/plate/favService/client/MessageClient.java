package com.plate.favService.client;

import com.plate.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * create by chenzhihui on 2022/5/4.
 **/
@FeignClient("service-msg")
@Component
public interface MessageClient {

    @PostMapping("msgService/message/selectMessage/{userId}/{infoType}")
    R selectMessage(@PathVariable("userId") String userId,@PathVariable("infoType") Integer infoType);

    @PostMapping("msgService/message/getMessageById/{id}")
    public R getMessageById(@PathVariable("id") String id);


    @PostMapping("goodService/good/selectGood/{userId}/{infoType}")
    R selectGood(@PathVariable("userId") String userId, @PathVariable("infoType") Integer infoType);

    @PostMapping("goodService/good/getGoodById/{id}")
    public R getGoodById(@PathVariable("id") String id);

    @PostMapping("trendService/trend/selectTrend/{userId}/{infoType}")
    R selectTrend(@PathVariable("userId") String userId, @PathVariable("infoType") Integer infoType);

    @PostMapping("trendService/trend/getTrendById/{id}")
    public R getTrendById(@PathVariable("id") String id);
}
