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
@TableName("ts_sytech")
@ApiModel(description="适宜技术表")
public class TsSytech {

    @ApiModelProperty(value="适宜技术ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="适宜技术状态(0:停用 1:启用)")
    private Integer status;

    @ApiModelProperty(value="适宜技术名称")
    private String name;

    @ApiModelProperty(value="适宜技术描述")
    private String descrip;

    @ApiModelProperty(value="视频资源url")
    private String url;

    @ApiModelProperty(value="是否展示")
    private Integer display;

    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    @ApiModelProperty(value="创建时间")
    private Date createTime;
}
