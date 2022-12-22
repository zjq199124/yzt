package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaitTreatmentRo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("姓名/手机号搜索字段")
    private String search;

    @ApiModelProperty("科室id")
    private Long departmentId;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("当前页")
    private Integer currentPage = 1;

    @ApiModelProperty("页面容量")
    private Integer pageSize = 10;
}
