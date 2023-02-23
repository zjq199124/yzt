package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("his_department")
@ApiModel(description = "科室表")
public class HisDepartment implements Serializable {

    @TableId
    @ApiModelProperty(value = "科室ID(departmentId)")
    @TableField(value = "id")
    private String id;

    @ApiModelProperty(value = "科室名称")
    private String name;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date updateTime;

}
