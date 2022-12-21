package com.maizhiyu.yzt.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.maizhiyu.yzt.vo.TimeSlotInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("hs_appointment_system")
@ApiModel(description = "系统预约时段设置表")
public class HsAppointmentSystem extends Model<HsAppointmentSystem> {

    @ApiModelProperty("系统预约设置主键id")
    private Long id;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("是否有法定节假日：1是；0否")
    private Integer hasHolidays;

    @ApiModelProperty("生效时间")
    private Date effectTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("预约时段设置信息")
    private String timeSlotInfo;

    @ApiModelProperty("是否删除")
    private Integer isDel;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("时段信息对象")
    @TableField(exist = false)
    private TimeSlotInfoVo timeSlotInformationVo;
}






























