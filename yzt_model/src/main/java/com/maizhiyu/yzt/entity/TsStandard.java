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
@TableName("ts_standard")
@ApiModel(description="技术标准表")
public class TsStandard {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="技术ID")
    private Long tid;

    @ApiModelProperty(value="排序序号")
    private Integer sort;

    @ApiModelProperty(value="项目名称")
    private String name;

    @ApiModelProperty(value="项目内容")
    private String content;
}
