package com.maizhiyu.yzt.vo;

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

    @ApiModelProperty("分型得分")
    private double score;

    @ApiModelProperty("是否选中: 1:是；0：否")
    private Integer isCheck = 0;
}
