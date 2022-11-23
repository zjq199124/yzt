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
@Accessors(chain=true)
@TableName("ms_customer")
@ApiModel(description="客户表")
public class MsCustomer {

    @ApiModelProperty(value="客户ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="代理商ID")
    private Long agencyId;

    @ApiModelProperty(value="客户状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="微信开关(0:停用 1:启用)")
    private Integer wxswitch;

    @ApiModelProperty(value="客户名称")
    private String name;

    @ApiModelProperty(value="客户编码")
    private String username;

    @ApiModelProperty(value="客户地址")
    private String address;

    @ApiModelProperty(value="联系人列表")
    private String contacts;

    @ApiModelProperty(value="客户描述")
    private String descrip;

    @ApiModelProperty(value="头像地址")
    private String avatar;

    @ApiModelProperty(value = "地域省")
    private String province;

    @ApiModelProperty(value = "地域市")
    private String city;

    @ApiModelProperty(value = "位置纬度")
    private Double locationx;

    @ApiModelProperty(value = "位置经度")
    private Double locationy;

    @ApiModelProperty(value = "预约时段")
    private String timeslot;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;

}
