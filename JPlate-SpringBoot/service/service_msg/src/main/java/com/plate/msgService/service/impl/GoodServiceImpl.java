package com.plate.msgService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plate.msgService.client.UserClient;
import com.plate.msgService.entity.Good;
import com.plate.msgService.entity.vo.GoodQuery;
import com.plate.msgService.mapper.GoodMapper;
import com.plate.msgService.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czh
 * @since 2022-05-01
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

    //跨服务调用注入
    @Autowired
    private UserClient userClient;


    @Override
    public Map<String, Object> getGoodFrontList(Page<Good> page) {
        QueryWrapper<Good> wrapper = new QueryWrapper<>();

        //把分页数据封装到pageMessage里来
        Page<Good> pageGood = (Page<Good>) baseMapper.selectPage(page, wrapper);

        //1.获得了关于GoodList
        List<Good> records = pageGood.getRecords();
        long current = pageGood.getCurrent();
        long pages = pageGood.getPages();
        long size = pageGood.getSize();
        long total = pageGood.getTotal();
        System.out.println("records" + records);
        System.out.println("total    " + total);
        System.out.println("pages    " + pages);

        //当前是否有下页和上页
        boolean hasNext = pageGood.hasNext();
        boolean hasPrevious = pageGood.hasPrevious();

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
    public Map<String, Object> selectGoodByQuery(Page<Good> page, GoodQuery goodQuery) {

        QueryWrapper<Good> wrapper = new QueryWrapper<>();


        if(!StringUtils.isEmpty(goodQuery.getSort())){
            if(!goodQuery.getSort().equals("0")){
                wrapper.eq("sort",goodQuery.getSort());
            }
        }


        int low = goodQuery.getLow();
        if(low >0 ){
            wrapper.gt("price",low);
        }

        int high = goodQuery.getHigh();
        if(high >0 ){
            wrapper.lt("price",high);
        }

        if(goodQuery.getExchange() == 1){
            wrapper.orderByDesc("view_count");
        }else if(goodQuery.getExchange() == 0){
            //按照时间降序排序
            wrapper.orderByDesc("gmt_create");
        }else if(goodQuery.getExchange() == 2){
            wrapper.orderByDesc("price");
        }else{
            wrapper.orderByAsc("price");
        }



        //把分页数据封装到pageMessage里来
        Page<Good> pageGood = (Page<Good>) baseMapper.selectPage(page, wrapper);


        List<Good> records = pageGood.getRecords();
        long current = pageGood.getCurrent();
        long pages = pageGood.getPages();
        long size = pageGood.getSize();
        long total = pageGood.getTotal();
        System.out.println("records" + records);
        System.out.println("total    " + total);
        System.out.println("pages    " + pages);

        //当前是否有下页和上页
        boolean hasNext = pageGood.hasNext();
        boolean hasPrevious = pageGood.hasPrevious();

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
    public List<Good> selectGoodsByUserId(String userId) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Good> goods = baseMapper.selectList(queryWrapper);
        return goods;
    }
}
