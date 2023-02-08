package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@Validated
public class AppointmentRo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("之前已经存在的预约数据")
    private List<Long> preTaskIdList;

    @ApiModelProperty("预约数据列表")
    private List<BuPrescriptionItemTaskRo> buPrescriptionItemTaskRoList;

}
