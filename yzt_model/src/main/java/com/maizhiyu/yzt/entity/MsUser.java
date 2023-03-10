package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("ms_user")
@ApiModel(description="用户表")
public class MsUser implements Serializable {

    @ApiModelProperty(value="用户ID")
    @TableId(type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="插入时间")
    private Date createTime;

    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<Long> roleList;

    @ApiModelProperty(value = "部门列表")
    @TableField(exist = false)
    private List<Long> departmentList;
}
