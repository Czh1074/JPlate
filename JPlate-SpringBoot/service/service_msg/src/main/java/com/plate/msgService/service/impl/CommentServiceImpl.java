package com.plate.msgService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plate.msgService.entity.Comment;
import com.plate.msgService.mapper.CommentMapper;
import com.plate.msgService.service.CommentService;
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
 * @since 2022-05-02
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Map<String, Object> getCommentFrontList(Page<Comment> page) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("gmt_create");
        //把分页数据封装到pageMessage里来
        Page<Comment> pageComment = (Page<Comment>) baseMapper.selectPage(page, wrapper);


        List<Comment> records = pageComment.getRecords();
        long current = pageComment.getCurrent();
        long pages = pageComment.getPages();
        long size = pageComment.getSize();
        long total = pageComment.getTotal();
        System.out.println("records" + records);
        System.out.println("total    " + total);
        System.out.println("pages    " + pages);

        //当前是否有下页和上页
        boolean hasNext = pageComment.hasNext();
        boolean hasPrevious = pageComment.hasPrevious();

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
    public Map<String, Object> selectCommentByTrendId(Page<Comment> page, String trendId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();

        wrapper.orderByAsc("gmt_create");
        wrapper.eq("trend_id",trendId);
//        if(!StringUtils.isEmpty(goodQuery.getSort())){
//            if(!goodQuery.getSort().equals("0")){
//                wrapper.eq("sort",goodQuery.getSort());
//            }
//        }


//        int low = goodQuery.getLow();
//        if(low >0 ){
//            wrapper.gt("price",low);
//        }
//
//        int high = goodQuery.getHigh();
//        if(high >0 ){
//            wrapper.lt("price",high);
//        }
//
//        if(goodQuery.getExchange() == 1){
//            wrapper.orderByDesc("view_count");
//        }else if(goodQuery.getExchange() == 0){
//            //按照时间降序排序
//            wrapper.orderByDesc("gmt_create");
//        }else if(goodQuery.getExchange() == 2){
//            wrapper.orderByDesc("price");
//        }else{
//            wrapper.orderByAsc("price");
//        }



        //把分页数据封装到pageMessage里来
        Page<Comment> pageComment = (Page<Comment>) baseMapper.selectPage(page, wrapper);


        List<Comment> records = pageComment.getRecords();
        long current = pageComment.getCurrent();
        long pages = pageComment.getPages();
        long size = pageComment.getSize();
        long total = pageComment.getTotal();
        System.out.println("records" + records);
        System.out.println("total    " + total);
        System.out.println("pages    " + pages);

        //当前是否有下页和上页
        boolean hasNext = pageComment.hasNext();
        boolean hasPrevious = pageComment.hasPrevious();

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
