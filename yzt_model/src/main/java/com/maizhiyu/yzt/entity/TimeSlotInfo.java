package com.maizhiyu.yzt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotInfo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("上午开始时段")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date morningStart;

    @ApiModelProperty("上午结束时段")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date morningEnd;

    @ApiModelProperty("上午时段长度")
    private Integer morningTimeSlot;

    @ApiModelProperty("上午时段治疗数")
    private Integer morningTimeSlotQuantity;

    @ApiModelProperty("上午时段预览")
    private List<String> morningTimeSlotList;

    @ApiModelProperty("下午开始时段")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date afternoonStart;

    @ApiModelProperty("下午结束时段")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date afternoonEnd;

    @ApiModelProperty("下午时段长度")
    private Integer afternoonTimeSlot;

    @ApiModelProperty("下午时段治疗数")
    private Integer afternoonTimeSlotQuantity;

    @ApiModelProperty("下午时段预览")
    private List<String> afternoonTimeSlotList;
}
