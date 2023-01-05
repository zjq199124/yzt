package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain=true)
@TableName("ts_ass_operation")
@ApiModel(description="操作步骤表")
public class TsAssOperation {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="适宜技术ID")
    private Long sytechId;

    @ApiModelProperty(value="具体考核操作ID")
    private Long operationId;

    @ApiModelProperty(value="具体考核操作名称")
    private String operationName;

    @ApiModelProperty(value="总分值")
    private Integer score;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="是否删除")
    private Integer isDel;

    @ApiModelProperty(value = "细节")
    @TableField(exist = false)
    private List<TsAssOperationDetail> tsAssOperationDetailList;

}
