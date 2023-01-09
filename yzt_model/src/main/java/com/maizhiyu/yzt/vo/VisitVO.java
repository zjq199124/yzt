package com.maizhiyu.yzt.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maizhiyu.yzt.entity.BuVisit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(description = "随访列表")
public class VisitVO extends BuVisit {

    @ApiModelProperty(value = "患者姓名")
    private String name;


    @ApiModelProperty(value = "性别")
    private String sex;


    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "就诊时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "Asia/Shanghai")
    private Date diagnoseTime;

    @ApiModelProperty(value = "诊断")
    private String diagnoseName;

    @ApiModelProperty(value = "适宜技术")
    private String sytechName;

    @ApiModelProperty(value = "主治医生")
    private String doctorName;

    @ApiModelProperty(value = "操作人")
    private String cureUserName;

}
