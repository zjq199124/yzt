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
@Accessors(chain=true)
@TableName("his_doctor")
@ApiModel(description="HIS医生表")
public class HisDoctor implements Serializable {
    @TableId
    @ApiModelProperty(value="his端医生账号(doctorId)")
    private String id;

    @ApiModelProperty(value="his端医生昵称")
    @TableField("nickname")
    private String nickName;

    @ApiModelProperty(value="his端医生姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value="医生性别")
    private String sex;

    @ApiModelProperty(value="医生所属科室id")
    private String departmentIds;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date updateTime;
}
