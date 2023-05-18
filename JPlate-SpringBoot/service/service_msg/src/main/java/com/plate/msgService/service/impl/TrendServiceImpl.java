package com.plate.msgService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plate.commonutils.R;
import com.plate.msgService.client.UserClient;
import com.plate.msgService.entity.Trend;
import com.plate.msgService.entity.TrendFavoriteItem;
import com.plate.msgService.mapper.TrendMapper;
import com.plate.msgService.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czh
 * @since 2022-04-27
 */
@Service
public class TrendServiceImpl extends ServiceImpl<TrendMapper, Trend> implements TrendService {

    //跨服务调用注入
    @Autowired
    private UserClient userClient;

    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<Trend> selectAllBanner(){

        //查询所有的动态->根据 view_count 进行降序排序
        QueryWrapper<Trend> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        //last方法，拼接sql语句
        wrapper.last("limit 8");
        List<Trend> list = baseMapper.selectList(wrapper);
        return list;

    }

    @Override
    public Map<String, Object> getTrendFrontList(Page<Trend> page, Integer exchange) {
        QueryWrapper<Trend> wrapper = new QueryWrapper<>();

        if(exchange == 0){
            wrapper.orderByDesc("gmt_create");
        }else{
            wrapper.orderByDesc("view_count");
        }
        //把分页数据封装到pageTrend里来
        Page<Trend> pageTrend = (Page<Trend>) baseMapper.selectPage(page, wrapper);

        //1.获得了关于TrendList
        List<Trend> records = pageTrend.getRecords();
        //2.整合用户信息
        List<TrendFavoriteItem> trendFavoriteItems = new ArrayList<>();
        //3.遍历整合
        for(Trend trend: records){
            //2.通过遍历得到每一个userId
            R user = userClient.getUser(trend.getUserId());
            Map<String,String> userMap = (Map<String, String>) user.getData().get("user");
            String nickName = userMap.get("nickName");
            String salt = userMap.get("salt");
            //3.整合成trendFavoriteItems
            TrendFavoriteItem trendFavoriteItem = new TrendFavoriteItem();
            trendFavoriteItem.setTrend(trend);
            trendFavoriteItem.setSalt(salt);
            trendFavoriteItem.setNickName(nickName);
            trendFavoriteItems.add(trendFavoriteItem);
        }

        long current = pageTrend.getCurrent();
        long pages = pageTrend.getPages();
        long size = pageTrend.getSize();
        long total = pageTrend.getTotal();

        System.out.println("trendFavoriteItems" + trendFavoriteItems + "trendFavoriteItems长度" + trendFavoriteItems.size());
        System.out.println("total    " + total);
        System.out.println("pages    " + pages);

        //当前是否有下页和上页
        boolean hasNext = pageTrend.hasNext();
        boolean hasPrevious = pageTrend.hasPrevious();

        //把分页技术取出，转到map集合
        Map<String,Object> map = new HashMap<>();

        map.put("items", trendFavoriteItems);
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
    public List<Trend> getHotTrendFrontList() {

        QueryWrapper<Trend> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_Count");
        queryWrapper.last("limit 6");

        List<Trend> trends = baseMapper.selectList(queryWrapper);
        return trends;
    }

    //通过userId获得所有的trends
    @Override
    public List<Trend> selectTrendsByUserId(String userId) {
        QueryWrapper<Trend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        List<Trend> trends = baseMapper.selectList(queryWrapper);
        return trends;
    }
}
