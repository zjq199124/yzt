package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
@TableName("sch_zhongyao")
@ApiModel(description = "方案中药表")
public class SchZhongyao {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="所属疾病")
    private Long diseaseId;

    @ApiModelProperty(value="所属辨证")
    private Long syndromeId;

    @ApiModelProperty(value="中药名称")
    private String name;

    @ApiModelProperty(value="组成成分")
    private String component;

    @TableLogic
    private Integer flag;
}
