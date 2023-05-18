package com.plate.msgService.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.plate.commonutils.R;
import com.plate.commonutils.entiy.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * create by chenzhihui on 2022/5/17.
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Message对象", description="")
public class MessageFavoriteItem {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "兼职信息")
    private Message message;

    @ApiModelProperty(value = "用户头像")
    private String salt;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;
}
