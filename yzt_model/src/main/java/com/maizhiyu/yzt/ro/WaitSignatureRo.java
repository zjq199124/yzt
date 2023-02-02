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
public class WaitSignatureRo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("姓名/手机号搜索字段")
    private String search;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("预约状态：0：未预约；1:已预约")
    private Integer appointmentStatus;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date startDate;

    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date endDate;

    @ApiModelProperty("当前页")
    private Integer currentPage = 1;

    @ApiModelProperty("页面容量")
    private Integer pageSize = 10;
}
