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

    @ApiModelProperty("分型推荐匹配得分")
    private double score;

    @ApiModelProperty("是否选中: 1:是；0：否")
    private Integer isCheck = 0;

    @ApiModelProperty("分型列表中是否展示: 1:是；0：否")
    private Integer isShow = 0;
}
