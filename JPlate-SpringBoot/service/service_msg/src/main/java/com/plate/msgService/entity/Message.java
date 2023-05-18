package com.plate.msgService.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author czh
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Message对象", description="")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "兼职id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "兼职名称")
    private String name;

    @ApiModelProperty(value = "兼职内容")
    private String introduction;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "钖祫")
    private Integer salary;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "联系人")
    @TableField("contactName")
    private String contactName;

    @ApiModelProperty(value = "发布者id")
    private String userId;

    @ApiModelProperty(value = "访问量")
    private Integer viewCount;

    @ApiModelProperty(value = "收藏量")
    private Integer collectCount;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @ApiModelProperty(value = "是否收藏")
    private Boolean collect;


}
