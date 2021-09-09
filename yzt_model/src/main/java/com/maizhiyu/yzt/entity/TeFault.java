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
@TableName("te_fault")
@ApiModel(description="设备故障表")
public class TeFault {

    @ApiModelProperty(value = "故障ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备类型")
    private Integer type;

    @ApiModelProperty(value = "设备ID")
    private Long eid;

    @ApiModelProperty(value = "状态(1:已上报, 2:已解决, 3:已回访, 4:已评价)")
    private Integer status;

    @ApiModelProperty(value = "上报人ID")
    private Long reportUid;

    @ApiModelProperty(value = "上报人电话")
    private String reportPhone;

    @ApiModelProperty(value = "上报时间")
    private Date reportTime;

    @ApiModelProperty(value = "上报内容")
    private String reportContent;

    @ApiModelProperty(value = "上报照片")
    private String reportImages;

    @ApiModelProperty(value = "解决人ID")
    private Long resolveUid;

    @ApiModelProperty(value = "解决时间")
    private Date resolveTime;

    @ApiModelProperty(value = "解决内容")
    private String resolveContent;

    @ApiModelProperty(value = "回访人ID")
    private Long followupUid;

    @ApiModelProperty(value = "回访时间")
    private Date followupTime;

    @ApiModelProperty(value = "回访内容")
    private String followupContent;

    @ApiModelProperty(value = "评价人ID")
    private Long evaluationUid;

    @ApiModelProperty(value = "评价时间")
    private Date evaluationTime;

    @ApiModelProperty(value = "评价内容")
    private String evaluationContent;
}
