package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("te_model")
@ApiModel(description="设备型号表")
public class TeModel {

    @ApiModelProperty(value="型号ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="型号状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="设备类型")
    private Integer type;

    @ApiModelProperty(value="设备型号")
    private String name;

    @ApiModelProperty(value="生产厂商")
    private String mfrs;

    @ApiModelProperty(value="型号描述")
    private String descrip;
}
