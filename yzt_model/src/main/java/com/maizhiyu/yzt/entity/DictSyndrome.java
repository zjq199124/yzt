package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("dict_syndrome")
@ApiModel(description = "分型字典表")
public class DictSyndrome {

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="分型名称")
    private String name;

    @ApiModelProperty(value="编码")
    private String code;

    @ApiModelProperty(value="状态(1:已删除 0:未删除)")
    private Integer isDel;

    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value="修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value="备注说明")
    private String remark;
}
