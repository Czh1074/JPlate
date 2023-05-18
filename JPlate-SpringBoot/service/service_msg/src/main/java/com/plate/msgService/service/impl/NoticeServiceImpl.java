package com.plate.msgService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plate.msgService.entity.Notice;
import com.plate.msgService.mapper.NoticeMapper;
import com.plate.msgService.service.NoticeService;
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
 * @since 2022-04-30
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public Map<String, Object> getNoticeFrontList(Page<Notice> page) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("gmt_create");
        //把分页数据封装到pageMessage里来
        Page<Notice> pageNotice = (Page<Notice>) baseMapper.selectPage(page, wrapper);


        List<Notice> records = pageNotice.getRecords();
        long current = pageNotice.getCurrent();
        long pages = pageNotice.getPages();
        long size = pageNotice.getSize();
        long total = pageNotice.getTotal();
        System.out.println("records" + records);
        System.out.println("total    " + total);
        System.out.println("pages    " + pages);

        //当前是否有下页和上页
        boolean hasNext = pageNotice.hasNext();
        boolean hasPrevious = pageNotice.hasPrevious();

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
}
