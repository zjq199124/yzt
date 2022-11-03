package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("bu_outpatient")
@ApiModel(description="门诊预约表")
public class BuOutpatient implements Serializable {

    @ApiModelProperty(value="预约ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="预约单编码")
    private String code;

    @ApiModelProperty(value="状态(0:已取消 1:待付款 2:待取号 3:待就诊 4:已就诊)")
    private Integer status;

    @ApiModelProperty(value="预约类型")
    private Integer type;

    @ApiModelProperty(value="预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date time;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="科室ID")
    private Long departmentId;

    @ApiModelProperty(value="医生ID")
    private Long doctorId;

    @ApiModelProperty(value="排班ID")
    private Long scheduleId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="HIS中ID")
    private String hisId;

    @ApiModelProperty(value="其他系统的数据")
    private String extra;
}
