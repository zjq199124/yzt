package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BuCureRO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("签到id")
    private Long signatureId;

    @ApiModelProperty("适宜技术id")
    private Long entityId;

    @ApiModelProperty("适宜技术名称")
    private String tsName;

    @ApiModelProperty("适宜技术详情")
    private String tsDescription;

    @ApiModelProperty("患者id")
    private Long patientId;

    @ApiModelProperty("门诊id")
    private Long outpatientId;

    @ApiModelProperty("登记负责人")
    private Long registrantId;

    @ApiModelProperty("治疗负责人")
    private Long cureUserId;

    @ApiModelProperty("处方子项目id")
    private Long prescriptionItemId;

}
