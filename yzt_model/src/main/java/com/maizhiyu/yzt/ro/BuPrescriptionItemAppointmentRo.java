package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuPrescriptionItemAppointmentRo extends BaseRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("PsUser主键id")
    private Long psUserId;

    @ApiModelProperty("患者id列表")
    private List<Long> patientIdList;
}
























































