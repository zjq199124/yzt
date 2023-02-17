package com.maizhiyu.yzt.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 患者随访表(BuVisit)表实体类
 *
 * @author liuyzh
 * @since 2022-12-19 18:57:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "")
@TableName("bu_visit")
@SuppressWarnings("serial")
public class BuVisit extends Model<BuVisit> implements Serializable {
//    private static final long serialVersionUID = -75865880254136098L;

    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("治疗id")
    @NotNull
    private Long cureId;

    @ApiModelProperty("随访状态(1-正常，2-失访，3-死亡）")
    @NotNull
    private Integer status;

    @ApiModelProperty("疗效评定(1-治愈，2-好转，3-未愈)")
    @NotNull
    private Integer result;

    @ApiModelProperty("随访类型")
    @NotNull
    private Integer type;

    @ApiModelProperty("随访详情")
    @NotNull
    private String content;

    @ApiModelProperty("随访时间")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "Asia/Shanghai")
    private Date visitTime;

}
