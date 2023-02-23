package com.maizhiyu.yzt.entity;


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
@TableName("his_outpatient")
@ApiModel(description="门诊预约表")
public class HisOutpatient implements Serializable {

    @TableId
    @ApiModelProperty(value="预约ID(outpatientId)")
    @TableField(value = "id")
    private String id;

    @ApiModelProperty(value="his端医生ID")
    private String doctorId;

    @ApiModelProperty(value="his端患者ID")
    private String patientId;

    @ApiModelProperty(value="his端医生科室ID")
    private String departmentId;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date updateTime;
}
