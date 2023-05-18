package com.plate.servicebase.exceptionhandler;

import com.plate.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by chenzhihui on 2022/4/17.
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody //为了能返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了能返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理...");
    }

    //自定义异常
    @ExceptionHandler(PlateException.class)
    @ResponseBody //为了能返回数据
    public R error(PlateException e){
        log.error(e.getMessage()); //将发生异常的信息加入到日志文件中国
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
