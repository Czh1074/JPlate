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
@ApiModel(value="Good对象", description="")
public class GoodFavoriteItem {
    @ApiModelProperty(value = "商品信息")
    private Good good;

    @ApiModelProperty(value = "用户头像")
    private String salt;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;
}
