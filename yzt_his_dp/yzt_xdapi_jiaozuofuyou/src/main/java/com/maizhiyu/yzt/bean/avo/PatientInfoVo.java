package com.maizhiyu.yzt.bean.avo;

import com.maizhiyu.yzt.entity.HisDoctor;
import com.maizhiyu.yzt.entity.HisOutpatient;
import com.maizhiyu.yzt.entity.HisPatient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class PatientInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("his端门诊信息")
    private HisOutpatient hisOutpatient;

    @ApiModelProperty("his端患者信息")
    private HisPatient hisPatient;

    @ApiModelProperty("his端医生信息")
    private HisDoctor hisDoctor;
}
