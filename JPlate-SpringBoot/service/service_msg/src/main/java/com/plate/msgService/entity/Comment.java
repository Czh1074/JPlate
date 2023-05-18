package com.plate.msgService.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author czh
 * @since 2022-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "璇勮id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "鐢熸椿淇℃伅id")
    private String trendId;

    @ApiModelProperty(value = "鐢ㄦ埛id")
    private String userId;

    @ApiModelProperty(value = "鑱婂ぉ淇℃伅")
    private String message;

    @ApiModelProperty(value = "回复信息id")
    private String chatId;

    private Integer isDeleted;

    @ApiModelProperty(value = "浼氬憳澶村儚")
    private String avatar;

    @ApiModelProperty(value = "閻劍鍩涢弰鐢敌?")
    private String nickname;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    private Date gmtModefied;


}
