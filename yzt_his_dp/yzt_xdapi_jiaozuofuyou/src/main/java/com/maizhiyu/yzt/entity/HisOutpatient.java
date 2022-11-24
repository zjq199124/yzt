package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("ypt_Registration")
@ApiModel(description="门诊预约表")
public class HisOutpatient implements Serializable {

    @TableId
    @ApiModelProperty(value="预约ID")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value="预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date time;

    @ApiModelProperty(value="医生ID")
    private String doctorId;

    @ApiModelProperty(value="患者ID")
    private String patientId;

}