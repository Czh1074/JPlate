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
 * @since 2022-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Notice对象", description="")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "鍏憡id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "主题")
    private String project;

    @ApiModelProperty(value = "鍏憡鍐呭")
    private String introduction;

    @ApiModelProperty(value = "闂勫嫪娆?")
    private String appendix1;

    private String appendix2;

    private String appendix3;

    @ApiModelProperty(value = "璁块棶閲?")
    private Integer viewCount;

    @ApiModelProperty(value = "鏄惁灞曠ず")
    private Boolean isShow;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    private Integer collectCount;

    private Boolean collect;

    private String userId;


}
