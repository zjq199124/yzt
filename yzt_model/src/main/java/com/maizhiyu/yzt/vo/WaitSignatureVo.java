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

    @ApiModelProperty("处方适宜技术小项目id")
    private Long prescriptionItemId;

    @ApiModelProperty("门诊id")
    private Long outpatientId;

    @ApiModelProperty("患者姓名")
    private String name;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("科室名称")
    private String dname;

    @ApiModelProperty("预约日期")
    private Date appointmentDate;

    @ApiModelProperty("预约时段")
    private Date timeSlot;

    @ApiModelProperty("疾病名称")
    private String disease;

    @ApiModelProperty("适宜技术名称")
    private String tsName;

    @ApiModelProperty("开方次数")
    private Integer quantity;

    @ApiModelProperty("已治疗次数")
    private Integer treatmentQuantity;
}
