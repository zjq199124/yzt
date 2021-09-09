package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("te_equip")
@ApiModel(description="设备表")
public class TeEquip {

    @ApiModelProperty(value="设备ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="设备状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="运行状态(0:待机 1:预热 2:治疗 3:暂停 4:结束)")
    private Integer state;

    @ApiModelProperty(value="代理商ID")
    private Long agencyId;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="设备类型")
    private Integer type;

    @ApiModelProperty(value="设备型号")
    private Integer modelId;

    @ApiModelProperty(value="设备编码")
    private String code;

    @ApiModelProperty(value="设备标识")
    private String identity;

    @ApiModelProperty(value="质保期(年)")
    private Integer warranty;

    @ApiModelProperty(value="购买时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date buyTime;

    @ApiModelProperty(value="心跳时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date heartTime;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    private Date createTime;
}
