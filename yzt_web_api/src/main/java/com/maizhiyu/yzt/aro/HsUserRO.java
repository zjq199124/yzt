package com.maizhiyu.yzt.aro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HsUserRO {

    @Data
    @ApiModel
    @Validated
    public static class AddUserRO {

        @NotBlank
        @ApiModelProperty(value="用户名称")
        private String username;

        @ApiModelProperty(value="用户密码")
        private String password;

        @NotBlank
        @ApiModelProperty(value="用户真名")
        private String realname;

        @ApiModelProperty(value="用户昵称，his登录账户")
        private String nickname;

        @ApiModelProperty(value="用户手机")
        private String phone;

        @ApiModelProperty(value="用户性别(0:女，1:男)")
        private Integer sex;

        @NotBlank
        @ApiModelProperty(value="HIS内医生ID")
        private String hisId;

        @ApiModelProperty(value="HIS内附加数据")
        private String hisExtra;
    }
}
