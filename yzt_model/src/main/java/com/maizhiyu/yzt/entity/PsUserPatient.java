package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("ps_user_patient")
@ApiModel(description = "用户患者关系表")
public class PsUserPatient {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "患者ID")
    private Long patientId;

}
