package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)
@TableName("te_fault_solution")
@ApiModel(description="故障方案表")
public class TeFaultSolution {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备类型")
    private Integer type;

    @ApiModelProperty(value = "方案名称")
    private String name;

    @ApiModelProperty(value = "故障描述")
    private String descrip;

    @ApiModelProperty(value = "解决方案")
    private String solution;
}
