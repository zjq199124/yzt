package com.maizhiyu.yzt.bean.aro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;


@Data
@ApiModel
@Validated
public class BasicPagerRO {

    @ApiModelProperty(value="开始页数(默认1)", required = false)
    private Integer pageNum = 1;

    @ApiModelProperty(value="每页大小(默认100)", required = false)
    private Integer pageSize = 100;
}
