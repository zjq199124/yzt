package com.maizhiyu.yzt.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * className:SchZhongyaoHerbsRequest
 * Package:com.maizhiyu.yzt.request
 * Description:
 *
 * @DATE:2021/12/20 10:53 上午
 * @Author:2101825180@qq.com
 */

@Data
public class SchZhongyaoHerbsRequest {

    @ApiModelProperty(value="中药方案id")
    private Long zyId;

    @ApiModelProperty(value="中药id")
    private Long herbsId;

    @ApiModelProperty(value="数量")
    private BigDecimal num;

}
