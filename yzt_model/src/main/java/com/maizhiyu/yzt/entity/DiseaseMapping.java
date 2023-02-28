package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("disease_mapping")
@ApiModel(description="疾病映射表映射表")
public class DiseaseMapping implements Serializable {
    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="云平台药品编码")
    private Long code;

    @ApiModelProperty(value="云平台疾病id")
    private Long diseaseId;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="云平台疾病名称")
    private String name;

    @ApiModelProperty(value="拼音")
    private String pinyin;

    @ApiModelProperty(value="缩写")
    private String abbr;

    @ApiModelProperty(value="his内部编码")
    private String hisCode;

    @ApiModelProperty(value="his内名称")
    private String hisName;

    @ApiModelProperty(value="状态：0：未删除；1：已删除")
    private Integer isDel;


    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="备注")
    private String remark;
}
