package com.maizhiyu.yzt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotDetailVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("时段名称")
    private String timeSlot;

    @ApiModelProperty("是否预约;1是,0否")
    private Integer hasAppointment;

    @ApiModelProperty("是否超过上限;1:是,0否")
    private Integer overLimit;

    @ApiModelProperty("适宜技术小项目预约详情数据主键id")
    private Long buPrescriptionTaskId;
}
