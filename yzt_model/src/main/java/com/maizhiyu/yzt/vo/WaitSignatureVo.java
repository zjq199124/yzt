package com.maizhiyu.yzt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaitSignatureVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("处治小项目任务id")
    private Long prescriptionItemTaskId;

    @ApiModelProperty("处方适宜技术小项目id")
    private Long prescriptionItemId;

    @ApiModelProperty("门诊id")
    private Long outpatientId;

    @ApiModelProperty("处方子项适宜技术id")
    private Long entityId;

    @ApiModelProperty("患者id")
    private Long patientId;

    @ApiModelProperty("患者姓名")
    private String name;

    @ApiModelProperty("患者手机号")
    private String phone;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("科室名称")
    private String dname;

    @ApiModelProperty("预约日期")
    private Date appointmentDate;

    @ApiModelProperty("预约时段")
    private String timeSlot;

    @ApiModelProperty("签到时间")
    private Date signatureTime;

    @ApiModelProperty("疾病名称")
    private String disease;

    @ApiModelProperty("适宜技术名称")
    private String tsName;

    @ApiModelProperty("开方次数")
    private Integer quantity;

    @ApiModelProperty("已治疗次数")
    private Integer treatmentQuantity;

    @ApiModelProperty("就诊时间")
    private Date outpatientTime;
}
