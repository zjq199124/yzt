package com.maizhiyu.yzt.entity;


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
//TableName("ypt_Staff")
@TableName("his_doctor")
@ApiModel(description="HIS医生表")
public class HisDoctor implements Serializable {
    @TableId
    @ApiModelProperty(value="his端医生账号(hisDoctorId)")
    private String username;

    @ApiModelProperty(value="his端医生昵称")
    private String nickname;

    @ApiModelProperty(value="his端医生姓名")
    private String realname;

//    @ApiModelProperty(value="医生性别")
//    private Integer sex;

    @ApiModelProperty(value="创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date createTime;
}
