package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("bu_check")
@ApiModel(description = "检查报告表")
public class BuCheck implements Serializable {

    @ApiModelProperty(value = "检查ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "门诊ID")
    private Long outpatientId;

    @ApiModelProperty(value = "检查类型")
    private Integer type;

    @ApiModelProperty(value = "多媒体数据id")
    private Long multimediaId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value = "患者身份证号")
    private String idCard;

    @ApiModelProperty(value = "患者手机")
    private String mobile;

    @ApiModelProperty(value = "检查名称")
    @TableField(exist = false)
    private String name;

}
