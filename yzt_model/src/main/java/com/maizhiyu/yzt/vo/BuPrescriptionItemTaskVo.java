package com.maizhiyu.yzt.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuPrescriptionItemTaskVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("处治小项目任务id")
    private Long prescriptionItemTaskId;

    @ApiModelProperty("处方适宜技术小项目id")
    private Long prescriptionItemId;

    @ApiModelProperty("处方id")
    private Long prescriptionId;

    @ApiModelProperty("门诊id")
    private Long outpatientId;

    @ApiModelProperty("处方子项适宜技术id")
    private Long entityId;

    @ApiModelProperty("疾病名称")
    private Long diseaseId;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;

    @ApiModelProperty("预约时段")
    private String timeSlot;

    @ApiModelProperty("签到时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signatureTime;

    @ApiModelProperty("治疗时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cureStartTime;

    @ApiModelProperty("疾病名称")
    private String disease;

    @ApiModelProperty("操作方法")
    private String operation;

    @ApiModelProperty("适宜技术名称")
    private String tsName;

    @ApiModelProperty("适宜技术详情")
    private String detail;

    @ApiModelProperty("开方次数")
    private Integer quantity;

    @ApiModelProperty("已治疗次数")
    private Integer treatmentQuantity;

    @ApiModelProperty("治疗状态；0未治疗；1治疗中；2治疗已结束")
    private Integer cureStatus;

    @ApiModelProperty("治疗负责人")
    private String cureUserName;

    @ApiModelProperty("签到登记人")
    private String registrantName;

    @ApiModelProperty("用户id")
    private Long customerId;

    @ApiModelProperty("就诊时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outpatientTime;

    @ApiModelProperty("技术方案")
    private List<BuPrescriptionItem> buPrescriptionItemList;
}
