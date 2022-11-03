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
@TableName("tx_bgq_run")
@ApiModel(description="拔罐器运行表")
public class TxBgqRun {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "运行标识")
    private String runid;

    @ApiModelProperty(value = "运行状态(1:运行 2:停止)")
    private Integer status;

    @ApiModelProperty(value = "颈部温度(1留罐 2走罐 3闪罐)")
    private Integer mode;

    @ApiModelProperty(value = "时长设置")
    private Integer duration;

    @ApiModelProperty(value = "腰部温度")
    private Integer pressure;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endTime;
}
