package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("te_warn")
@ApiModel(description="设备预警表")
public class TeWarn {

    @ApiModelProperty(value = "预警ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备ID")
    private Long eid;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "运行ID")
    private String runid;

    @ApiModelProperty(value = "预警类型")
    private Integer type;

    @ApiModelProperty(value = "预警时间")
    private Date time;
}
