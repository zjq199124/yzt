package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
@TableName("hs_resource")
@ApiModel(description = "资源表")
public class HsResource implements Serializable {

    @ApiModelProperty(value = "资源ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "资源类型(1:菜单 2:按钮)")
    private Integer type;

    @ApiModelProperty(value = "资源层级")
    private Integer level;

    @ApiModelProperty(value = "父级标识")
    private Long parent;

    @ApiModelProperty(value = "资源标识")
    private String identity;

    @ApiModelProperty(value = "资源标题")
    private String title;

    @ApiModelProperty(value = "资源取值")
    private String value;

    @ApiModelProperty(value = "资源描述")
    private String descrip;

    @ApiModelProperty(value = "图标标识")
    private String icon;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否删除")
    private Integer isDel;
}