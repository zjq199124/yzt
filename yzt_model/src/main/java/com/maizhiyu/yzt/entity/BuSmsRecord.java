package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 治疗表(BuCure)表实体类
 * 
 * @author liuyzh
 * @since 2022-12-19 18:41:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "")
@TableName("bu_sms_record")
@SuppressWarnings("serial")
public class BuSmsRecord extends Model<BuSmsRecord> implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("短信模板")
    private String templateCode;

    @ApiModelProperty("请求序号")
    private String requestId;

    @ApiModelProperty("请求参数")
    private String reqParams;

    @ApiModelProperty("返回参数")
    private String resParams;

    @ApiModelProperty("发送结果：1成功；0失败")
    private Integer result;

    @ApiModelProperty("创建时间")
    private Date createTime;
}



















