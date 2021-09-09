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
@TableName("tx_xzc_data")
@ApiModel(description="熏蒸床数据表")
public class TxXzcData {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "运行标识")
    private String runid;

    @ApiModelProperty(value = "当前时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date time;

    @ApiModelProperty(value = "颈部药液温度")
    private Integer neckLiquidTemp;

    @ApiModelProperty(value = "颈部体感温度")
    private Integer neckSkinTemp;

    @ApiModelProperty(value = "腰部药液温度")
    private Integer waistLiquidTemp;

    @ApiModelProperty(value = "腰部体感温度")
    private Integer waistSkinTemp;
}
