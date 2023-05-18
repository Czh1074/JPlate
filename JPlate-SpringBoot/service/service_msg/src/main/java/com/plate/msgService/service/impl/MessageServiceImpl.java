package com.plate.msgService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plate.msgService.entity.Message;
import com.plate.msgService.mapper.MessageMapper;
import com.plate.msgService.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czh
 * @since 2022-04-29
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    //分页查询兼职信息的方法
    @Override
    public Map<String, Object> getMessageFrontList(Page<Message> page,Integer exchange) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();

        if(exchange == 0){
            wrapper.orderByDesc("gmt_create");
        }else{
            wrapper.orderByDesc("view_count");
        }
        //把分页数据封装到pageMessage里来
        Page<Message> pageMessage = (Page<Message>) baseMapper.selectPage(page, wrapper);


        List<Message> records = pageMessage.getRecords();
        long current = pageMessage.getCurrent();
        long pages = pageMessage.getPages();
        long size = pageMessage.getSize();
        long total = pageMessage.getTotal();
        System.out.println("records" + records);
        System.out.println("total    " + total);
        System.out.println("pages    " + pages);

        //当前是否有下页和上页
        boolean hasNext = pageMessage.hasNext();
        boolean hasPrevious = pageMessage.hasPrevious();

        //把分页技术取出，转到map集合
        Map<String,Object> map = new HashMap<>();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public List<Message> getMessageFavoriteList(String userId, Integer infoType) {

        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq("user_Id",userId);
        wrapper.eq("info_Type",infoType);

        List<Message> messages = baseMapper.selectList(wrapper);

        return messages;
    }

    @Override
    public List<Message> selectMessagesByUserId(String userId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        List<Message> messages = baseMapper.selectList(queryWrapper);
        return messages;
    }

    //    @Override
//    public Map<String, Object> getMessageFavoriteList(Page<Message> page, String userId, Integer infoType) {
//        QueryWrapper<Message> wrapper = new QueryWrapper<>();
//
//        wrapper.eq("user_id",userId);
//        wrapper.eq("info_type",infoType);
//        //把分页数据封装到pageMessage里来
//        Page<Message> pageMessage = (Page<Message>) baseMapper.selectPage(page, wrapper);
//
//
//        List<Message> records = pageMessage.getRecords();
//        long current = pageMessage.getCurrent();
//        long pages = pageMessage.getPages();
//        long size = pageMessage.getSize();
//        long total = pageMessage.getTotal();
//        System.out.println("records" + records);
//        System.out.println("total    " + total);
//        System.out.println("pages    " + pages);
//
//        //当前是否有下页和上页
//        boolean hasNext = pageMessage.hasNext();
//        boolean hasPrevious = pageMessage.hasPrevious();
//
//        //把分页技术取出，转到map集合
//        Map<String,Object> map = new HashMap<>();
//
//        map.put("items", records);
//        map.put("current", current);
//        map.put("pages", pages);
//        map.put("size", size);
//        map.put("total", total);
//        map.put("hasNext", hasNext);
//        map.put("hasPrevious", hasPrevious);
//
//        //map返回
//        return map;
//    }

}
