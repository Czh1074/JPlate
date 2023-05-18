package com.plate.msgService.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * create by chenzhihui on 2022/5/17.
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Trend对象", description="")
public class TrendFavoriteItem {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "兼职信息")
    private Trend trend;

    @ApiModelProperty(value = "用户头像")
    private String salt;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;
}
