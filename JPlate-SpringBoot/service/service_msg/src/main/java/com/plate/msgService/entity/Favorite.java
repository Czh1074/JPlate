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
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Favorite对象", description="")
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "鏀惰棌id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "鐢ㄦ埛id")
    private String userId;

    @ApiModelProperty(value = "鏂囩珷id")
    private String infoId;

    @ApiModelProperty(value = "鏂囩珷绫诲埆")
    private Integer infoType;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;


}
