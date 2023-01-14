package com.maizhiyu.yzt.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutpatientAppointmentRo extends BaseRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号，姓名搜索字段")
    private String search;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("预约状态")
    private Integer state;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("PsUser主键id")
    private Long psUserId;

    @ApiModelProperty("患者id")
    private Long patientId;

    @ApiModelProperty("查询起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startDate;

    @ApiModelProperty("查询结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endDate;
}
























































