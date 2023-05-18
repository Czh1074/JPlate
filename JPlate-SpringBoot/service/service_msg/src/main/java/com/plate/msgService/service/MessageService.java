package com.plate.msgService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plate.msgService.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czh
 * @since 2022-04-29
 */
public interface MessageService extends IService<Message> {
    //分页查询兼职信息的方法
    Map<String, Object> getMessageFrontList(Page<Message> pageMessage,Integer exchange);

    List<Message> getMessageFavoriteList(String userId, Integer infoType);

    public List<Message> selectMessagesByUserId(String userId);
}
