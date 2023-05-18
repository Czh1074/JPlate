package com.plate.msgService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.plate.msgService.entity.Good;
import com.plate.msgService.entity.vo.GoodQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czh
 * @since 2022-05-01
 */
public interface GoodService extends IService<Good> {

    Map<String, Object> getGoodFrontList(Page<Good> pageGood);

    Map<String, Object> selectGoodByQuery(Page<Good> pageGood, GoodQuery goodQuery);

    public List<Good> selectGoodsByUserId(String userId);
}
