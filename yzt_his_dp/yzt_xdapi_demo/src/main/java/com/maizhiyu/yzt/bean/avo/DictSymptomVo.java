package com.maizhiyu.yzt.bean.avo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictSymptomVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("云平台症状主键id")
    private Long id;

    @ApiModelProperty("云平台症状名称")
    private String content;
}
