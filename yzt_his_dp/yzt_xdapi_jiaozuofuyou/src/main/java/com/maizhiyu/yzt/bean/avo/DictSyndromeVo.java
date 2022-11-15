package com.maizhiyu.yzt.bean.avo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictSyndromeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("云平台分型主键id")
    private Long id;

    @ApiModelProperty("云平台分型名称")
    private String name;
}
