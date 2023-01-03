package com.maizhiyu.yzt.bean.axo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

public class HsDepartmentXO {

    @Data
    @ApiModel
    @Validated
    public static class AddDepartmentXO {

        @ApiModelProperty(value = "所属客户")
        private Long customerId;

        @ApiModelProperty(value = "科室名称")
        private String dname;

        @ApiModelProperty(value = "HIS内科室ID")
        private String hisId;

    }
}
