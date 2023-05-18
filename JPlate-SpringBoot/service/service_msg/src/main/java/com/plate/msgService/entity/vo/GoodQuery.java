package com.plate.msgService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by chenzhihui on 2022/4/26.
 **/
@Data
public class GoodQuery {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别,模糊查询")
    private String sort;

    @ApiModelProperty(value = "最低价格,模糊查询")
    private int low;

    @ApiModelProperty(value = "最高价格,模糊查询")
    private int high;

    @ApiModelProperty(value = "访问量,模糊查询")
    private int viewCount;

    @ApiModelProperty(value = "价格,模糊查询")
    private double price;

    @ApiModelProperty(value = "时间,模糊查询")
    private int time;

    @ApiModelProperty(value = "时间,模糊查询")
    private int exchange;

}
