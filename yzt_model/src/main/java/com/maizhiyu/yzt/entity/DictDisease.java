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
@TableName("dict_disease")
@ApiModel(description = "疾病字典表")
public class DictDisease {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="编码")
    private String code;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="名称近义词")
    private String jyName;

    @ApiModelProperty(value="备注说明")
    private String remark;

    @ApiModelProperty(value="是否删除：1是；0否")
    private Integer isDel;
}
