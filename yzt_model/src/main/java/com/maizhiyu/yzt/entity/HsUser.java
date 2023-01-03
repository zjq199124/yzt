package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain=true)
@TableName("hs_user")
@ApiModel(description="用户表")
public class HsUser implements Serializable {

    @ApiModelProperty(value="用户ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="所属客户")
    private Long customerId;

    @ApiModelProperty(value="用户状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="用户名称")
    private String userName;

    @ApiModelProperty(value="用户密码")
    private String password;

    @ApiModelProperty(value="用户昵称")
    private String nickName;

    @ApiModelProperty(value="用户真名")
    private String realName;

    @ApiModelProperty(value="用户手机")
    private String phone;

    @ApiModelProperty(value="用户性别(0:女，1:男)")
    private Integer sex;

    @ApiModelProperty(value="头像地址")
    private String avatar;

    @ApiModelProperty(value="是否医生")
    private Integer isDoctor;

    @ApiModelProperty(value="是否治疗师")
    private Integer isTherapist;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value="HIS中ID")
    private String hisId;

    @ApiModelProperty(value="角色列表")
    @TableField(exist = false)
    private List<Long> roleList;

    @ApiModelProperty(value="科室列表")
    @TableField(exist = false)
    private List<Long> departmentList;
}
