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
 * @since 2022-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Trend对象", description="")
public class Trend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生活信息id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "主题")
    private String project;

    @ApiModelProperty(value = "图片1")
    private String picture1;

    @ApiModelProperty(value = "图片2")
    private String picture2;

    @ApiModelProperty(value = "图片3")
    private String picture3;

    @ApiModelProperty(value = "信息内容")
    private String introduction;

    @ApiModelProperty(value = "访问量")
    private Integer viewCount;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "点赞量")
    private Integer agreeCount;

    @ApiModelProperty(value = "评论量")
    private Integer commentCount;

    @ApiModelProperty(value = "收藏量")
    private Integer collectCount;

    @ApiModelProperty(value = "是否收藏")
    private Boolean collect;

    @ApiModelProperty(value = "是否展示")
    private Boolean isShow;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
