package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("tx_xzc_run")
@ApiModel(description="熏蒸床运行表")
public class TxXzcRun {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "运行标识")
    private String runid;

    @ApiModelProperty(value = "运行状态(0:待机 1:预热 2:治疗 3:暂停 4:结束)")
    private Integer status;

    @ApiModelProperty(value = "时长设置")
    private Integer duration;

    @ApiModelProperty(value = "颈部温度")
    private Double neckTemp;

    @ApiModelProperty(value = "腰部温度")
    private Double waistTemp;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endTime;

    @ApiModelProperty(value = "预警信息")
    private Integer warn;
}
