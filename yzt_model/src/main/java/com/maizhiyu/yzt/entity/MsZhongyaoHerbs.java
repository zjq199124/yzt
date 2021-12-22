package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * className:MsZhongyaoHerbs
 * Package:com.maizhiyu.yzt.entity
 * Description:
 *
 * @DATE:2021/12/17 2:11 下午
 * @Author:2101825180@qq.com
 */

@Data
@Accessors(chain=true)
@TableName("ms_zhongyao_herbs")
@ApiModel(description="中药方案关联表")
public class MsZhongyaoHerbs {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="中药方案id")
    private Long zyId;

    @ApiModelProperty(value="中药id")
    private Long herbsId;

    @ApiModelProperty(value="数量")
    private BigDecimal num;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

}
