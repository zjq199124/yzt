package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * className:HsCustomerHerbs
 * Package:com.maizhiyu.yzt.entity
 * Description:
 *
 * @DATE:2021/12/16 10:25 上午
 * @Author:2101825180@qq.com
 */

@Data
@Accessors(chain = true)
@TableName("hs_customer_herbs")
@ApiModel(description = "客户药材关联表")
public class HsCustomerHerbs {

    @ApiModelProperty(value="客户药材ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="药材ID")
    private Long herbsId;

    @ApiModelProperty(value="库存")
    private BigDecimal inventory;

    @ApiModelProperty(value="单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @TableLogic
    private Integer flag;

}
