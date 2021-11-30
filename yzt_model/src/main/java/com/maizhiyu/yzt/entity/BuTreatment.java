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
import java.util.List;

@Data
@Accessors(chain=true)
@TableName("bu_treatment")
@ApiModel(description="治疗预约表")
public class BuTreatment implements Serializable {

    @ApiModelProperty(value="预约ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="预约单编码")
    private String code;

    @ApiModelProperty(value="治疗单编码(即处方单编码)")
    private String tcode;

    @ApiModelProperty(value="预约状态(0:已取消 1:待打印 2:待治疗 3:治疗中 4:已治疗 5:已反馈 6:已评价 7:已随访 8:异常)")
    private Integer status;

    @ApiModelProperty(value="预约类型")
    private Integer type;

    @ApiModelProperty(value="预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date time;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="科室ID")
    private Long departmentId;

    @ApiModelProperty(value="医生ID(治疗师)")
    private Long therapistId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="处方ID")
    private Long prescriptionId;

    @ApiModelProperty(value="条目ID")
    private Long itemId;

    @ApiModelProperty(value="项目ID")
    private Long projectId;

    @ApiModelProperty(value="异常情况")
    private String exception;

    @ApiModelProperty(value="患者反馈")
    private String feedback;

    @ApiModelProperty(value="患者评价")
    private String evaluation;

    @ApiModelProperty(value="随访记录")
    private String followup;

    @ApiModelProperty(value="随访人")
    private Long followupUid;

    @ApiModelProperty(value="随访时间")
    private Date followupTime;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
}
