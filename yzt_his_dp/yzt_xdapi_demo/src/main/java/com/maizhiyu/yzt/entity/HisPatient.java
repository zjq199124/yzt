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
@TableName("ypt_Cehr_Patient")
//@TableName("his_patient")
@ApiModel(description="HIS患者表")
public class HisPatient implements Serializable {

    @TableId
    @ApiModelProperty(value="his端患者ID(patientId)")
    @TableField(value = "id")
    private String id;

    @ApiModelProperty(value="患者姓名")
    private String name;

    @ApiModelProperty(value="患者性别")
    private Integer sex;

    @ApiModelProperty(value="出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date birthday;

    @ApiModelProperty(value="患者手机")
    private String phone;

    @ApiModelProperty(value="身份证号")
    private String idCard;
}
