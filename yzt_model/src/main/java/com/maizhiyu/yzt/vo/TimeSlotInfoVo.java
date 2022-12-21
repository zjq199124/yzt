package com.maizhiyu.yzt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotInfoVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("上午开始时段")
    private String morningStart;

    @ApiModelProperty("上午结束时段")
    private String morningEnd;

    @ApiModelProperty("上午时段长度")
    private Integer morningTimeSlot;

    @ApiModelProperty("上午时段治疗数")
    private Integer morningTimeSlotQuantity;

    @ApiModelProperty("上午时段预览")
    private List<String> morningTimeSlotList;

    @ApiModelProperty("下午开始时段")
    private String afternoonStart;

    @ApiModelProperty("下午结束时段")
    private String afternoonEnd;

    @ApiModelProperty("下午时段长度")
    private Integer afternoonTimeSlot;

    @ApiModelProperty("下午时段治疗数")
    private Integer afternoonTimeSlotQuantity;

    @ApiModelProperty("下午时段预览")
    private List<String> afternoonTimeSlotList;
}
