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
@TableName("ts_sytech_item")
@ApiModel(description="适宜技术考核项目表")
public class TsSytechItem {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="适宜技术ID")
    private Long sytechId;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="标题")
    private String title;

    @ApiModelProperty(value="满分分数")
    private Integer fullScore;

    @ApiModelProperty(value="技术规范")
    private String specification;

    @ApiModelProperty(value="扣分说明")
    private String descrip;

}
