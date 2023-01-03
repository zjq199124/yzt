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
@TableName("ts_ass_operation_detail")
@ApiModel(description="操作细节表")
public class TsAssOperationDetail {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="具体考核操作ID")
    private Long operationId;

    @ApiModelProperty(value="具体考核操作步骤")
    private String detail;


    @ApiModelProperty(value = "评分说明")
    private String mark;

    @ApiModelProperty(value = "评分等级")
    private String grade;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="是否删除")
    private Integer isDel;
}
