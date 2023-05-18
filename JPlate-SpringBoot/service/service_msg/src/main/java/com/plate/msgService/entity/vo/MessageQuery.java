package com.plate.msgService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

/**
 * create by chenzhihui on 2022/4/27.
 **/
@Data
public class MessageQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "兼职名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "最低价格,模糊查询")
    private int low;

//    @ApiModelProperty(value = "日期,模糊查询")
//    private Date createTime;
}
