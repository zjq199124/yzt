package com.maizhiyu.yzt.bean.axo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

public class BuPatientXO {

    @Data
    @ApiModel
    @Validated
    public static class AddPatientXO {

        @ApiModelProperty(value="患者姓名")
        private String name;

        @ApiModelProperty(value="患者性别")
        private Integer sex;

//        @ApiModelProperty(value="出生日期")
//        @JsonFormat(pattern = "yyyy-MM-dd",timezone="Asia/Shanghai")
//        private Date birthday;

        @ApiModelProperty(value="患者手机")
        private String phone;

        @ApiModelProperty(value="身份证号")
        private String idcard;

        @ApiModelProperty(value="HIS中ID")
        private String hisId;

        @ApiModelProperty(value="HIS数据(建议json格式)")
        private String extra;
    }
}
