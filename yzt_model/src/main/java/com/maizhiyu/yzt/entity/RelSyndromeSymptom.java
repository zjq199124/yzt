package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("rel_syndrome_symptom")
@ApiModel(description = "辩证分型中间表")
public class RelSyndromeSymptom extends Model<RelSyndromeSymptom> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("症状主键id")
    private Long symptomId;

    @ApiModelProperty("分型主键id")
    private Long syndromeId;

    @ApiModelProperty("分型主键id")
    private Long diseaseId;

    @ApiModelProperty("0:未删除；1：已删除")
    private Integer isDel;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("备注")
    private String remark;
}































