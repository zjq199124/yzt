package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("sch_chengyao")
@ApiModel(description = "方案成药表")
public class SchChengyao {

    @ApiModelProperty(value = "成药ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="所属疾病")
    private Long diseaseId;

    @ApiModelProperty(value="所属辨证")
    private Long syndromeId;

    @ApiModelProperty(value="成药名称")
    private String name;

    @ApiModelProperty(value="组成成分")
    private String component;

    @ApiModelProperty(value="功能说明")
    private String function;

    @ApiModelProperty(value="禁忌症状")
    private String contrain;

    @ApiModelProperty(value="注意事项")
    private String attention;
}
