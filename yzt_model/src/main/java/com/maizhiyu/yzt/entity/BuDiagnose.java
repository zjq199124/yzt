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
@TableName("bu_diagnose")
@ApiModel(description="诊断表")
public class BuDiagnose implements Serializable {

    @ApiModelProperty(value="诊断ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="诊断状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="科室ID")
    private Long departmentId;

    @ApiModelProperty(value="医生ID")
    private Long doctorId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="门诊ID")
    private Long outpatientId;

    @ApiModelProperty(value="疾病id")
    private Long diseaseId;

    @ApiModelProperty(value="疾病名称")
    private String disease;

    @ApiModelProperty(value="辨证分型")
    private String syndrome;

    @ApiModelProperty(value="辨证分型id")
    private Long syndromeId;

    @ApiModelProperty(value="症状列表")
    private String symptoms;

    @ApiModelProperty(value="症状列表")
    private String symptomIds;

    @ApiModelProperty(value="既病史")
    private String medicalHistory;

    @ApiModelProperty(value="过敏史")
    private String allergicHistory;

    @ApiModelProperty(value="家族史")
    private String familyHistory;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty("是否删除；1是，0否")
    private Integer isDel;

    @ApiModelProperty("姓名")
    @TableField(exist = false)
    private String name;

    @ApiModelProperty("手机号")
    @TableField(exist = false)
    private String phone;
}
