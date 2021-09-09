package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("ms_agency")
@ApiModel(description="代理商表")
public class MsAgency {

    @ApiModelProperty(value="代理商ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="代理商状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="代理商名称")
    private String name;

    @ApiModelProperty(value="代理商地址")
    private String address;

    @ApiModelProperty(value="联系人列表")
    private String contacts;

    @ApiModelProperty(value="代理商描述")
    private String descrip;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;
}
