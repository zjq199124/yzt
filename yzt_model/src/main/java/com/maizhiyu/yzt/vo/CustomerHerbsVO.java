package com.maizhiyu.yzt.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * className:MsHerbsVO
 * Package:com.maizhiyu.yzt.vo
 * Description:
 *
 * @DATE:2021/12/16 11:22 上午
 * @Author:2101825180@qq.com
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "客户药材")
public class CustomerHerbsVO {

    @ApiModelProperty(value="客户药材ID")
    private Long id;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="药材ID")
    private Long herbsId;

    @ApiModelProperty(value="库存")
    private BigDecimal inventory;

    @ApiModelProperty(value="单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value="药材名称")
    private String herbsName;

    @ApiModelProperty(value="单位")
    private String unit;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
