package com.plate.msgService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by chenzhihui on 2022/4/24.
 **/
@Data
public class NoticeQuery {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主题,模糊查询")
    private String project;
}
