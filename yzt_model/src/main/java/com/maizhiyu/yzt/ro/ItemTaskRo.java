package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTaskRo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("处治小项目id")
    private Long prescriptionItemId;

    @ApiModelProperty("任务id")
    private Long prescriptionItemTaskId;

    @ApiModelProperty("签到登记人id")
    private Long registrantId;

    @ApiModelProperty("治疗负责人id")
    private Long cureUserId;

    @ApiModelProperty("搜索字段")
    private String search;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("开始日期")
    private Date startDate;

    @ApiModelProperty("结束日期")
    private Date endDate;

    @ApiModelProperty("当前页")
    private Integer currentPage = 1;

    @ApiModelProperty("页面容量")
    private Integer pageSize = 10;
}




















