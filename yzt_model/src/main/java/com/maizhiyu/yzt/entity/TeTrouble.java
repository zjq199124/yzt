package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("te_trouble")
@ApiModel(description="设备问题表(常见问题)")
public class TeTrouble {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="型号状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="级别")
    private Integer level;

    @ApiModelProperty(value="父级ID")
    private Long parent;

    @ApiModelProperty(value="设备类型")
    private Integer etype;

    @ApiModelProperty(value="问题分类")
    private Integer cate;

    @ApiModelProperty(value="问题标题")
    private String title;

    @ApiModelProperty(value="问题内容")
    private String content;
}
