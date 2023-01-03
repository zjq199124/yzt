package com.maizhiyu.yzt.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BuCureSearchRO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("手机号/姓名")
    private String search;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("治疗查询起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date cureStartDate;

    @ApiModelProperty("治疗查询结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date cureEndDate;

    @ApiModelProperty("当前页")
    private Integer currentPage = 1;

    @ApiModelProperty("页面容量")
    private Integer pageSize = 10;
}
