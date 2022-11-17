package com.maizhiyu.yzt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelSyndromeSymptomVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("云平台症状主键id")
    private Long symptomId;

    @ApiModelProperty("云平台分型主键id")
    private Long syndromeId;
}
