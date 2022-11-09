package com.maizhiyu.yzt.bean.avo;

import com.maizhiyu.yzt.entity.YptMedicant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public class YptMedicantVO {

    @Data
    @ApiModel
    public static class AddMedicantVO extends YptMedicant {
    }


    @Data
    @ApiModel
    public static class GetMedicantVO extends YptMedicant {
    }


    @Data
    @ApiModel
    public static class GetMedicantListVO extends YptMedicant {
    }


    @Data
    @ApiModel
    public static class UploadMedicantHisVO {
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
