package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description="门诊预约表")
public class YptOutpatient implements Serializable {

    @TableId
    @ApiModelProperty(value="预约ID(outpatientId)")
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty(value="his端医生ID")
    private Long doctorId;

    @ApiModelProperty(value="his端患者ID")
    private String patientId;


    @ApiModelProperty(value="his的outpatientId")
    private Long hisId;
}
