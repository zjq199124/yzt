package com.maizhiyu.yzt.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public class BuDiagnoseRO {

    @Data
    @ApiModel
    @Validated
    public static class GetRecommendRO {

        @ApiModelProperty(value = "疾病名称")
        private String disease;

        @ApiModelProperty(value = "云平台疾病id")
        private Long diseaseId;

        @ApiModelProperty(value = "分型id列表")
        private List<Long> symptomIdList;

        @ApiModelProperty(value = "分型id列表")
        private List<Long> syndromeIdList;

        @ApiModelProperty(value = "适宜技术id")
        private Long sytechId;

        @ApiModelProperty(value = "客户名称")
        private String customerName;

        @ApiModelProperty(value = "his方患者id")
        private Long patientId;

        @ApiModelProperty(value = "his方门诊预约id")
        private Long outpatientId;

        @ApiModelProperty(value = "his方医生id")
        private Long hisDoctorId;

        @ApiModelProperty(value = "客户id")
        private Long customerId;
    }
}
