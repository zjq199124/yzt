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
@TableName("ps_user")
@ApiModel(description="用户表")
public class PsUser implements Serializable {

    @ApiModelProperty(value="用户ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="用户状态")
    private Integer status;

    @ApiModelProperty(value="openid")
    private String openid;

    @ApiModelProperty(value="unionid")
    private String unionid;

    @ApiModelProperty(value="用户昵称")
    private String nickname;

    @ApiModelProperty(value="用户手机")
    private String phone;

    @ApiModelProperty(value="用户性别(0:女，1:男)")
    private Integer sex;

    @ApiModelProperty(value="所在国家")
    private String country;

    @ApiModelProperty(value="所在省份")
    private String province;

    @ApiModelProperty(value="所在城市")
    private String city;

    @ApiModelProperty(value="头像地址")
    private String avatar;

    @ApiModelProperty(value="位置经度")
    private String longitude;

    @ApiModelProperty(value="位置纬度")
    private String latitude;

    @ApiModelProperty(value="位置精度")
    @TableField("`precision`")
    private String precision;

    @ApiModelProperty(value="现在国家")
    private String realtimeCountry;

    @ApiModelProperty(value="现在省份")
    private String realtimeProvince;

    @ApiModelProperty(value="现在城市")
    private String realtimeCity;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

}
