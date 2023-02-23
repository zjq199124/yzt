package com.maizhiyu.yzt.utils.infrared;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@ApiModel(description = "红外报告信息")
public class InfraredResult {
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 出生日期
     */
    @ApiModelProperty(value="出生日期")
    private String birthday;

    /**
     * 性别（1男，0女)
     */
    @ApiModelProperty(value="性别（1男，0女)")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @NotBlank(message = "创建时间不能为空")
    private String createTime;

    /**
     * 身份证
     */
    @ApiModelProperty(value="身份证")
    @NotBlank(message = "身份证不能为空")
    private String idCard;

    /**
     * 中医报告地址
     */
    @ApiModelProperty(value="中医报告地址")
    @NotBlank(message = "中医报告地址不能为空")
    private String tcmUrl;

    /**
     * 西医报告地址
     */
    @ApiModelProperty(value="西医报告地址")
    private String wmUrl;
}
