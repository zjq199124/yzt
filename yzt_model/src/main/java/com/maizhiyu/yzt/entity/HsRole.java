package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@TableName("hs_role")
@ApiModel(description = "角色表")
public class HsRole {

    @ApiModelProperty(value = "角色ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属客户")
    private Long customerId;

    @ApiModelProperty(value = "角色状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value = "角色名称")
    private String rolename;

    @ApiModelProperty(value = "角色描述")
    private String descrip;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @ApiModelProperty(value = "资源列表")
    @TableField(exist = false)
    private List<Long> resourceList;
}
