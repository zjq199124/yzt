package com.maizhiyu.yzt.vo;

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

    @ApiModelProperty("该症状所属的分型的id")
    private Long syndromeId;

    @ApiModelProperty("是否选中: 1:是；0：否")
    private Integer isCheck = 0;
}
