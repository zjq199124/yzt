package com.maizhiyu.yzt.bean.aro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class YptDiseaseRO {

    @Data
    @ApiModel
    @Validated
    public static class AddDiseaseRO {
        @NotBlank
        @ApiModelProperty(value="编码")
        private String code;

        @NotBlank
        @ApiModelProperty(value="名称")
        private String name;

        @NotBlank
        @ApiModelProperty(value="拼音")
        private String pinyin;

        @NotBlank
        @ApiModelProperty(value="缩写")
        private String abbr;

        @ApiModelProperty(value="his内编码")
        private String hiscode;

        @ApiModelProperty(value="his内名称")
        private String hisname;
    }

    @Data
    @ApiModel
    @Validated
    public static class SetDiseaseRO {
        @NotNull
        @ApiModelProperty(value="ID")
        private Integer id;

        @ApiModelProperty(value="编码")
        private String code;

        @ApiModelProperty(value="名称")
        private String name;

        @ApiModelProperty(value="拼音")
        private String pinyin;

        @ApiModelProperty(value="缩写")
        private String abbr;

        @ApiModelProperty(value="his内编码")
        private String hiscode;

        @ApiModelProperty(value="his内名称")
        private String hisname;
    }

    @Data
    @ApiModel
    @Validated
    public static class DelDiseaseRO {
        @NotNull
        @ApiModelProperty(value = "ID")
        private Integer id;
    }

    @Data
    @ApiModel
    @Validated
    public static class GetDiseaseRO {
        @NotNull
        @ApiModelProperty(value = "ID")
        private Integer id;
    }

    @Data
    @ApiModel
    @Validated
    public static class GetDiseaseListRO extends BasicPagerRO {
        @NotNull
        @ApiModelProperty(value = "查询词")
        private String term;
    }
}
