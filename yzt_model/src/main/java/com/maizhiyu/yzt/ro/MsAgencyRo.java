package com.maizhiyu.yzt.ro;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description="代理商表")
public class MsAgencyRo implements Serializable {

    @ApiModelProperty(value="代理商id")
    private Long id;

    @ApiModelProperty(value="代理商名称")
    @NotBlank(message = "代理商名称不能为空")
    private String name;

    @ApiModelProperty(value="代理商状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="省")
    @NotBlank(message = "省份不能为空")
    private String province;

    @ApiModelProperty(value="市")
    @NotBlank(message = "城市不能为空")
    private String city;

    @ApiModelProperty(value="区")
    @NotBlank(message = "地区不能为空")
    private String district;

    @ApiModelProperty(value="代理商详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String address;

    @ApiModelProperty(value="联系人")
    private String contacts;

    @ApiModelProperty(value="代理商联系电话")
    private String phone;

    @ApiModelProperty(value="代理商描述")
    private String descrip;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;

}
