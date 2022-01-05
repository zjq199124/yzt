package com.maizhiyu.yzt.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * className:HerbsVO
 * Package:com.maizhiyu.yzt.vo
 * Description:
 *
 * @DATE:2021/12/17 5:39 下午
 * @Author:2101825180@qq.com
 */

@Data
public class SchZhongyaoHerbsVO {

    private Long id;

    @ApiModelProperty(value="中药方案id")
    private Long zyId;

    @ApiModelProperty(value="中药id")
    private Long herbsId;

    @ApiModelProperty(value="中药方案名称")
    private String zyName;

    @ApiModelProperty(value="中药名称")
    private String herbsName;

    @ApiModelProperty(value="单位")
    private String unit;

    @ApiModelProperty(value="数量")
    private BigDecimal num;

    @ApiModelProperty(value="单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
