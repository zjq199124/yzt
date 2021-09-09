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
@TableName("te_maintain")
@ApiModel(description="设备维护表")
public class TeMaintain {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备ID")
    private Long eid;

    @ApiModelProperty(value = "设备编码")
    private String code;

    @ApiModelProperty(value = "维护类型")
    private Integer type;

    @ApiModelProperty(value = "维护次数")
    private Integer number;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "维护时间")
    private Date time;
}
