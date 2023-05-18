package com.plate.msgService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by chenzhihui on 2022/4/27.
 **/
@Data
public class TrendQuery {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主题,模糊查询")
    private String project;

    @ApiModelProperty(value = "0时间、1热度")
    private Integer exchange;

}
