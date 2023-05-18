package com.plate.msgService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.plate.msgService.entity.Notice;


import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czh
 * @since 2022-04-30
 */
public interface NoticeService extends IService<Notice> {
    Map<String, Object> getNoticeFrontList(Page<Notice> pageNotice);
}
