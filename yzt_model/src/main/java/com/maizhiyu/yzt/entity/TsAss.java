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

    @ApiModelProperty(value="考核时间")
    private Date creatTime;

    @ApiModelProperty(value="更新时间")
    private Date endTime;

    @ApiModelProperty(value="是否删除（0：否 1：是）")
    private Date isDel;

}
