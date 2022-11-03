package com.maizhiyu.yzt.bean.avo;

import com.maizhiyu.yzt.entity.YptDisease;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class YptDiseaseVO {

    @Data
    @ApiModel
    public static class AddDiseaseVO extends YptDisease {
    }


    @Data
    @ApiModel
    public static class GetDiseaseVO extends YptDisease {
    }


    @Data
    @ApiModel
    public static class GetDiseaseListVO extends YptDisease {
    }


    @Data
    @ApiModel
    public static class UploadDiseaseHisVO {
        @ApiModelProperty(value = "成功个数")
        private Integer succeed = 0;

        @ApiModelProperty(value = "失败个数")
        private Integer failed = 0;

        @ApiModelProperty(value = "错误个数")
        private Integer error = 0;

        @ApiModelProperty(value = "成功列表")
        private List<String> succeedList = new ArrayList<>();

        @ApiModelProperty(value = "失败列表")
        private List<String> failedList = new ArrayList<>();

        @ApiModelProperty(value = "错误列表")
        private List<String> errorList = new ArrayList<>();

        public void succeedPlus() {
            succeed++;
        }

        public void failedPlus() {
            failed++;
        }

        public void errorPlus() {
            error++;
        }
    }
}
