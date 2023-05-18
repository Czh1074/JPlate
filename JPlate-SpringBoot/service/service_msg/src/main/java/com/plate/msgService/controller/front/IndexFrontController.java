package com.plate.msgService.controller.front;

import com.plate.commonutils.R;
import com.plate.msgService.entity.Trend;
import com.plate.msgService.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by chenzhihui on 2022/4/27.
 **/
@RestController
@RequestMapping("trendService/indexfront")
public class IndexFrontController {

    @Autowired
    private TrendService trendService;

    //查询前八条热门课程
    @PostMapping("index")
    public R index(){
        List<Trend> trends = trendService.selectAllBanner();
        return R.ok().data("trends",trends);
    }

}
