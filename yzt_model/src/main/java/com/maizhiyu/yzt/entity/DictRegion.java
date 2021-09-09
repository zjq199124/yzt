package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("dict_region")
@ApiModel(description = "地域字典表")
public class DictRegion {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="父级ID")
    private Integer parent;

    @ApiModelProperty(value="地域级别(1:洲 2:国 3:省 4:市 5:县)")
    private Integer level;

    @ApiModelProperty(value="地域取值")
    private Integer value;

    @ApiModelProperty(value="显示内容")
    private String content;

    @ApiModelProperty(value="备注说明")
    private String note;
}
