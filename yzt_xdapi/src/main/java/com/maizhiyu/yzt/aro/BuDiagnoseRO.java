package com.maizhiyu.yzt.aro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

public class BuDiagnoseRO {

    @Data
    @ApiModel
    @Validated
    public static class GetRecommendRO {

        @NotNull
        @ApiModelProperty(value="疾病名称")
        private String disease;
    }
}
