package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain=true)
@TableName("ms_resource")
@ApiModel(description="资源表")
public class MsResource implements Serializable {

    @ApiModelProperty(value="资源ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="资源类型(1:菜单 2:按钮)")
    private Integer type;

    @ApiModelProperty(value="资源层级")
    private Integer level;

    @ApiModelProperty(value="父级标识")
    private Long parent;

    @ApiModelProperty(value="资源标识")
    private String identity;

    @ApiModelProperty(value="资源标题")
    private String title;

    @ApiModelProperty(value="资源取值")
    private String value;

    @ApiModelProperty(value="资源描述")
    private String descrip;
}