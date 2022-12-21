package com.maizhiyu.yzt.ro;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
@Validated
public class BuPrescriptionItemAppointmentItemRo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value="处方技术明细预约表id")
    private Long prescriptionItemAppointmentId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="门诊预约id")
    private Long outpatientId;

    @ApiModelProperty(value="预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date appointmentDate;

    @ApiModelProperty(value="预约时段")
    private String timeSlot;

    @ApiModelProperty("星期数")
    private Integer weekday;

}
