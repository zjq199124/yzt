package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * className:MsExaminationPaper
 * Package:com.maizhiyu.yzt.entity
 * Description:
 *
 * @DATE:2021/12/10 4:06 下午
 * @Author:2101825180@qq.com
 */

@Data
@TableName("ms_examination_paper")
@ApiModel(description="适宜技术考核试卷表")
public class MsExaminationPaper implements Serializable {

    @ApiModelProperty(value="检查ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="适宜技术ID")
    private Long sytechId;

    @ApiModelProperty(value="优秀分数")
    private Integer a;

    @ApiModelProperty(value="良好分数")
    private Integer b;

    @ApiModelProperty(value="差分数")
    private Integer c;

    @ApiModelProperty(value="试卷名称")
    private String title;

    @TableLogic
    private Integer flag;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;


}
