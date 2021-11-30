package com.maizhiyu.yzt.ro;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@ApiModel
@Validated
public class MsCustomerTimeslotRO {

    @NotNull
    @ApiModelProperty(value="客户ID", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value="预约时段", required = true)
    private String timeslot;

}
