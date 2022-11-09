package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain=true)
@TableName("request")
@ApiModel(description="请求记录")
public class YptRequest implements Serializable {

    @TableId
    @ApiModelProperty(value="ID")
    private Integer id;

    @ApiModelProperty(value="IP")
    private String ip;

    @ApiModelProperty(value="URL")
    private String url;

    @ApiModelProperty(value="时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
    private Date time;

    @ApiModelProperty(value="请求数据")
    private String request;

    @ApiModelProperty(value="响应数据")
    private String response;
}
