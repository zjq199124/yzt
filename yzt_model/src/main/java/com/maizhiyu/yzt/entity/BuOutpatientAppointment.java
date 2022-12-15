package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("bu_outpatient_appointment")
@ApiModel(description="门诊预约表")
public class BuOutpatientAppointment extends Model<BuOutpatientAppointment> {

    @ApiModelProperty(value="预约表主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="患者ID")
    private Long patientId;

    @ApiModelProperty(value="门诊预约id")
    private Long outpatientId;

    @ApiModelProperty(value="科室id")
    private Long departmentId;

    @ApiModelProperty(value="诊断id")
    private Long diagnoseId;

    @ApiModelProperty(value="就诊时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date outpatientTime;

    @ApiModelProperty("预约状态 1：未预约；2：预约中;3:预约完成")
    private Integer state;

    @ApiModelProperty("是否删除；1：是；0：否")
    private Integer IsDel;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
