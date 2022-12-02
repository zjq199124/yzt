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
@TableName("sch_sytech")
@ApiModel(description = "方案适宜技术表")
public class SchSytech {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="所属疾病")
    private Long diseaseId;

    @ApiModelProperty(value="所属辨证")
    private Long syndromeId;

    @ApiModelProperty(value="所属技术")
    private Long sytechId;

    @ApiModelProperty(value="客户id")
    private Long customerId;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="技术详情")
    private String detail;

    @ApiModelProperty(value="操作方法")
    private String operation;

    @ApiModelProperty(value="是否推荐 1是；0否")
    private Integer recommend;

    @ApiModelProperty(value="分型名称")
    private String syndromeName;

    @ApiModelProperty(value="疾病名称")
    private String diseaseName;
}
