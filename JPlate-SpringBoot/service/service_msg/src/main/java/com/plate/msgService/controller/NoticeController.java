package com.plate.msgService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.plate.commonutils.R;
import com.plate.msgService.client.UserClient;
import com.plate.msgService.entity.Notice;
import com.plate.msgService.entity.vo.NoticeQuery;
import com.plate.msgService.service.NoticeService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czh
 * @since 2022-04-24
 */
@RestController
@RequestMapping("/noticeService/notice")
public class NoticeController {
    //把service注入
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserClient userClient;

    //查询所有公告信息操作
    //rest风格
    @PostMapping("findAll")
    public R findAllNotice(){
        //调用service的方法来实现所有操作
        val list = noticeService.list(null);
        return R.ok().data("items",list);
    }

    //分页查询家教信息方法
    @PostMapping("pageNotice/{current}/{limit}")
    public R pageListNotice(@PathVariable long current, @PathVariable long limit){

        //创建page对象
        Page<Notice> pageNotice = new Page<>(current,limit);

        //调用方法的时候底层封装，把分页的所有数据封装到pageEduMessage对象里面
        noticeService.page(pageNotice,null);

        long total = pageNotice.getTotal(); //记录总记录数
        List<Notice> records = pageNotice.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    @PostMapping("pageNoticeCondition/{current}/{limit}")
    public R pageNoticeCondition(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) NoticeQuery noticeQuery){

        //创建一个page对象
        Page<Notice> pageNotice = new Page<>(current,limit);

        QueryWrapper<Notice> wrapper = new QueryWrapper<>();

        //多条件组合查询，动态sql
        //判断条件值是否为空，如不为空则拼接
        String project = noticeQuery.getProject();

        if(!StringUtils.isEmpty(project)){
            wrapper.like("project", project);
        }



        //调用方法实现条件查询分页
        noticeService.page(pageNotice,wrapper);

        long total = pageNotice.getTotal(); //记录总记录数
        List<Notice> records = pageNotice.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map).data("total",total).data("rows",records);
    }

    //添加文章接口
    @PostMapping("save")
    public R save(@RequestBody Notice notice){

        System.out.println(notice);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        notice.setId(uuid);
        notice.setGmtCreate(new Date());
        boolean save = noticeService.save(notice);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //删除文章接口
    @DeleteMapping("{id}")
    public R delNoticeById(@PathVariable String id){

        boolean flag = noticeService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //----------------------------------------------------------------公告栏前台
    @PostMapping("pageNoticeFrontList/{current}/{limit}")
    public R pageNoticeFrontList(@PathVariable long current, @PathVariable long limit){

        //创建一个page对象
        Page<Notice> pageNotice = new Page<>(current, limit);

        Map<String,Object> map = noticeService.getNoticeFrontList(pageNotice);

        return R.ok().data(map);
    }

    //查询兼职详细信息
    @PostMapping("getNoticeInfoById/{id}")
    public R getNoticeInfoById(@PathVariable String id){

        Notice notice = noticeService.getById(id);

        //获得userId2，然后返回相应的该动态发布者信息给前端展示头像和昵称
        String userId2 = notice.getUserId();
        R rUser = userClient.getUser(userId2);
        //把rUser--R转化为User类型
        Object user = rUser.getData().get("user");

        notice.setViewCount(notice.getViewCount()+1);
        noticeService.updateById(notice);


        return R.ok().data("notice",notice).data("user",user);
    }
}

