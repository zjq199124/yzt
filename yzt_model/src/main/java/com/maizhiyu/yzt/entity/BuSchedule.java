package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("bu_schedule")
@ApiModel(description="医生排班表")
public class BuSchedule implements Serializable {

    @ApiModelProperty(value="排班ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="医院ID")
    private Long customerId;

    @ApiModelProperty(value="科室ID")
    private Long departmentId;

    @ApiModelProperty(value="医生ID")
    private Long doctorId;

    @ApiModelProperty(value="出诊日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date date;

    @ApiModelProperty(value="时段类型(1:上午 2:下午 3:晚上)")
    private Integer timeType;

    @ApiModelProperty(value="放号数量")
    private Integer number;

    @TableField("`interval`")
    @ApiModelProperty(value="间隔时间")
    private Integer interval;

    @ApiModelProperty(value="挂号金额")
    private Float price;

    @ApiModelProperty(value="放号时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date openTime;

    @ApiModelProperty(value="开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startTime;

    @ApiModelProperty(value="结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endTime;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
}
