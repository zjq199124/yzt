package com.maizhiyu.yzt.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("ts_ass")
@ApiModel(description="操作考核表")
public class TsAss {
    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="适宜技术ID")
    private Long sytechId;

    @ApiModelProperty(value="考官ID")
    private Long examinerId;

    @ApiModelProperty(value="考核对象ID")
    private Long therapistId;

    @ApiModelProperty(value = "考核状态(0:未开始 1:考核中 3:考核结束)")
    private Integer status;

    @ApiModelProperty(value = "总分")
    private Integer totalScore;

    @ApiModelProperty(value="考核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="是否删除（0：否 1：是）")
    @TableLogic
    private Integer isDel;

    @TableField(exist = false)
    private String sytechName;

    @TableField(exist = false)
    private String examinerName;

    @TableField(exist = false)
    private String therapistName;
}
