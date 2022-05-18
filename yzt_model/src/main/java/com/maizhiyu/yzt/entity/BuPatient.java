package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("bu_patient")
@ApiModel(description="患者表")
public class BuPatient implements Serializable {

    @ApiModelProperty(value="患者ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="客户ID")
    private Long customerId;

    @ApiModelProperty(value="患者编码")
    private String code;

    @ApiModelProperty(value="患者状态(0:停用 1:启用 ……)")
    private Integer status;

    @ApiModelProperty(value="患者姓名")
    private String name;

    @ApiModelProperty(value="年龄")
    private Integer nl;

    @ApiModelProperty(value="患者性别")
    private Integer sex;

    @ApiModelProperty(value="出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date birthday;

    @ApiModelProperty(value="患者手机")
    private String phone;

    @ApiModelProperty(value="身份证号")
    private String idcard;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="其他系统的数据")
    private String extra;
}
