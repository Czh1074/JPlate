package com.plate.msgService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.plate.msgService.entity.Trend;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czh
 * @since 2022-04-27
 */
public interface TrendService extends IService<Trend> {

    public List<Trend> selectAllBanner();

    Map<String, Object> getTrendFrontList(Page<Trend> pageTrend, Integer exchange);

    List<Trend> getHotTrendFrontList();

    public  List<Trend> selectTrendsByUserId(String userId);
}
