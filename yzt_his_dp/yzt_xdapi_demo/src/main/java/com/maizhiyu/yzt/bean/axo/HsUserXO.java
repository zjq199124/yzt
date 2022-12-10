package com.maizhiyu.yzt.bean.axo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

public class HsUserXO {

    @Data
    @ApiModel
    @Validated
    public static class AddUserXO {

        @ApiModelProperty(value="用户名称")
        private String username;

        @ApiModelProperty(value="用户密码")
        private String password;

        @ApiModelProperty(value="用户真名")
        private String realname;

        @ApiModelProperty(value="用户性别(0:女，1:男)")
        private Integer sex;

        @ApiModelProperty(value="HIS内医生ID")
        private String hisId;

        @ApiModelProperty(value="HIS内附加数据")
        private String hisExtra;
    }
}
