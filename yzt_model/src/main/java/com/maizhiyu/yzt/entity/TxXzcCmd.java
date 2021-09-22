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
@TableName("tx_xzc_cmd")
@ApiModel(description="熏蒸床命令记录表")
public class TxXzcCmd {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "运行标识")
    private String runid;

    @ApiModelProperty(value = "命令状态(0:已作废 1:待执行 3:已执行)")
    private Integer status;

    @ApiModelProperty(value = "命令标识(1:开机 2:关机 3:开始 4:暂停 5:继续 6:设置 7:结束)")
    private Integer cmd;

    @ApiModelProperty(value = "设备状态(0:待机 1:预热 2:治疗 3:暂停 4:结束)")
    private Integer sysState;

    @ApiModelProperty(value = "颈部温度")
    private Double neckTemp;

    @ApiModelProperty(value = "腰部温度")
    private Double waistTemp;

    @ApiModelProperty(value = "额外数据")
    private String extraData;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value = "执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date executeTime;
}
