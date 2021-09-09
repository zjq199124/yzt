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
@TableName("dict_common")
@ApiModel(description = "疾病字典表")
public class DictCommon {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="父级ID")
    private Long parent;

    @ApiModelProperty(value="类别")
    private String cate;

    @ApiModelProperty(value="排序")
    private Integer sort;

    @ApiModelProperty(value="取值")
    private Integer value;

    @ApiModelProperty(value="显示内容")
    private String content;

    @ApiModelProperty(value="备注说明")
    private String note;
}
