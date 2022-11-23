package com.maizhiyu.yzt.utils.infrared;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(description = "红外报告信息")
public class InfraredResult {
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
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
    private Integer sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private String createTime;

    /**
     * 身份证
     */
    @ApiModelProperty(value="身份证")
    private String IDCard;

    /**
     * 中医报告地址
     */
    @ApiModelProperty(value="中医报告地址")
    private String tcmUrl;

    /**
     * 西医报告地址
     */
    @ApiModelProperty(value="西医报告地址")
    private String wmUrl;
}
