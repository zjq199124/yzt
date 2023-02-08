package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain=true)
@TableName("ps_family")
@ApiModel(description="家庭成员信息表")
public class PsFmaily {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value="用户昵称")
    private String nickname;

    @ApiModelProperty(value="用户手机")
    private String phone;

    @ApiModelProperty(value="身份证号码")
    private String idCard;

    @ApiModelProperty(value="用户性别(0:女，1:男)")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;
}
