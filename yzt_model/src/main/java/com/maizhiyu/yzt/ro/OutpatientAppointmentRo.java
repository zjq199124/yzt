package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutpatientAppointmentRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号，姓名搜索字段")
    private String search;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("预约状态")
    private Integer state;

    @ApiModelProperty("客户id")
    private Long customerId;
}
























































