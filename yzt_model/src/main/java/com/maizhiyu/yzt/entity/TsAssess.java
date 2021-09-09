package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("ts_assess")
@ApiModel(description="技术考核表")
public class TsAssess {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态")
    private Integer status;

    @ApiModelProperty(value="技术ID")
    private Long tid;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="考官ID")
    private Long examinerId;

    @ApiModelProperty(value="治疗师ID")
    private Long therapistId;

    @ApiModelProperty(value="考核分数")
    private Integer score;

    @ApiModelProperty(value="考核详情")
    private String detail;

    @ApiModelProperty(value="备注说明")
    private String note;

    @ApiModelProperty(value="考核时间")
    private Date time;
}
