package com.maizhiyu.yzt.vo;

import com.maizhiyu.yzt.entity.BuPrescriptionItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuCureVo implements Serializable {

    @ApiModelProperty("治疗主键id")
    private Long id;

    @ApiModelProperty("处方id")
    private Long prescriptionId;

    @ApiModelProperty("门诊id")
    private Long outpatientId;

    @ApiModelProperty("处方子项适宜技术id")
    private Long entityId;

    @ApiModelProperty("治疗负责人id")
    private Long cureUserId;

    @ApiModelProperty("治疗负责人姓名")
    private String cureUserName;

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

    @ApiModelProperty("疾病名称")
    private String disease;

    @ApiModelProperty("分型")
    private String syndrome;

    @ApiModelProperty("就诊日期")
    private Date outpatientTime;

    @ApiModelProperty("适宜技术名称")
    private String tsName;

    @ApiModelProperty("治疗状态1：进行中；2：已结束")
    private Integer status;

    @ApiModelProperty("登记人id")
    private Long registrantId;

    @ApiModelProperty("签到id")
    private Long signatureId;

    @ApiModelProperty("治疗适宜技术详情")
    private String tsDescription;

    @ApiModelProperty("治疗开始时间")
    private Date createTime;

    @ApiModelProperty("治疗方案集合")
    private List<BuPrescriptionItem> buPrescriptionItemList;
}
