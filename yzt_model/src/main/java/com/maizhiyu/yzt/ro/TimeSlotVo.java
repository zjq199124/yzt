package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotVo implements Serializable {

    private static final long serialVersionUID = -1L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @ApiModelProperty("预约表主键id")
    private Long outpatientAppointmentId;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("ps患者主键id")
    private Long psUserId;
}
