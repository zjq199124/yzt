package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class TxXzcCmdRo {

    @ApiModelProperty(value = "设备编码")
    @NotBlank
    private String code;

    @ApiModelProperty(value = "命令标识(1:开机 2:关机 3:开始 4:暂停 5:继续 6:设置 7:结束)")
    @NotNull
    private Integer cmd;

    @ApiModelProperty(value = "设备状态(0:待机 1:预热 2:治疗 3:暂停 4:结束)")
    private Integer sysState;

    @ApiModelProperty(value = "颈部温度")
    private  String neckTemp;

    @ApiModelProperty(value = "腰部温度")
    private String waistTemp;

}
