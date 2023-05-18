package com.plate.msgService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.plate.commonutils.R;
import com.plate.msgService.entity.Message;
import com.plate.msgService.entity.vo.MessageQuery;
import com.plate.msgService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by chenzhihui on 2022/4/29.
 **/
@RestController
@RequestMapping("/msgFrontService/message")
@CrossOrigin
public class MessageFrontController {

    @Autowired
    private MessageService messageService;


    //---------------------------------------------前台
    //条件查询兼职信息带分页的方法
    @PostMapping("pageMessageFrontList/{current}/{limit}/{exchange}")
    public R pageMessageFrontList(@PathVariable long current, @PathVariable long limit,
                                  @PathVariable int exchange){

        //创建一个page对象
        Page<Message> pageMessage = new Page<>(current, limit);

        Map<String,Object> map = messageService.getMessageFrontList(pageMessage,exchange);

        return R.ok().data(map);
    }

    //修改收藏字段+添加收藏夹的方法
    @PostMapping("updateMessageFrontList/{type}/{messageId}/{userId}")
    public R updateMessageFrontList(@PathVariable String type, @PathVariable String messageId,@PathVariable String userId){

        //通过messageId获得message对象
        Message message = messageService.getById(messageId);

        //对message对象进行collect字段的修改
        message.setCollect(!message.getCollect());

        //完成修改collect操作
        messageService.save(message);

        //todo：未完成调用service_user中favorite的保存

        return R.ok();
    }

}
