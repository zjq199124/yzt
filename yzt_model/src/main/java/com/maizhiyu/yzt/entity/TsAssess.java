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

    @ApiModelProperty(value="状态(0:未开始,2:已结束)")
    private Integer status;

    @ApiModelProperty(value="技术ID")
    private Long tid;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="考官ID")
    private Long examinerId;

    @ApiModelProperty(value="治疗师ID")
    private Long therapistId;

    @ApiModelProperty(value="试卷id")
    private Long examinationPaperId;

    @ApiModelProperty(value="备注说明")
    private String note;

    @ApiModelProperty(value="考核时间")
    private Date time;

    @ApiModelProperty(value="答题记录id")
    private Long problemRecordId;
}
