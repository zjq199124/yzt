package com.maizhiyu.yzt.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
public class BuDiagnoseVO {

    @Data
    @ApiModel
    public static class GetRecommendVO {
        @ApiModelProperty(value="中药处方列表")
        private List<ZhongyaoVO> zhongyaoList;

        @ApiModelProperty(value="成药处方列表")
        private List<ChengyaoVO> chengyaoList;

        @ApiModelProperty(value="协定处方列表")
        private List<XiedingVO> xiedingList;

        @ApiModelProperty(value="适宜技术列表")
        private List<ShiyiVO> shiyiList;
    }

    @Data
    @ApiModel
    public static class ZhongyaoVO {
        @ApiModelProperty(value="处方名称")
        private String name;

        @ApiModelProperty(value="适用症状")
        private String symptoms;

        @ApiModelProperty(value="药材组成")
        private List<ZhongyaoComponentVO> component;
    }


    @Data
    @ApiModel
    public static class ChengyaoVO {
        @ApiModelProperty(value="成药名称")
        private String name;

        @ApiModelProperty(value="适用症状")
        private String symptoms;

        @ApiModelProperty(value="组成成分")
        private String component;

        @ApiModelProperty(value="禁忌情况")
        private String contrain;

        @ApiModelProperty(value="注意事项")
        private String attention;
    }


    @Data
    @ApiModel
    public static class XiedingVO {
        @ApiModelProperty(value="处方名称")
        private String name;

        @ApiModelProperty(value="适用症状")
        private String symptoms;

        @ApiModelProperty(value="药材组成")
        private List<XiedingComponentVO> component;
    }


    @Data
    @ApiModel
    public static class ShiyiVO {
       /* @ApiModelProperty(value="编码")
        private String code;*/

        @ApiModelProperty(value="所属适宜技术id")
        private Long sytechId;

        @ApiModelProperty(value="技术名称")
        private String name;

        @ApiModelProperty(value="适用症状")
        private String symptoms;

        @ApiModelProperty(value="适用分型名称")
        private String syndromeName;

        @ApiModelProperty(value="详情")
        private String detail;

        @ApiModelProperty(value="操作")
        private String operation;

        @ApiModelProperty(value="客户id")
        private Long customerId;

        @ApiModelProperty("是否推荐：1是；0否")
        private Integer recommend;
    }


    @Data
    @ApiModel
    public static class ZhongyaoComponentVO {
        @ApiModelProperty(value="编码")
        private String code;

        @ApiModelProperty(value="名称")
        private String name;

        @ApiModelProperty(value="剂量")
        private Integer dosage;

        @ApiModelProperty(value="单位")
        private String unit;

        @ApiModelProperty(value="用法")
        private String usage;
    }


    @Data
    @ApiModel
    public static class XiedingComponentVO {
        @ApiModelProperty(value="编码")
        private String code;

        @ApiModelProperty(value="名称")
        private String name;

        @ApiModelProperty(value="剂量")
        private Integer dosage;

        @ApiModelProperty(value="单位")
        private String unit;

        @ApiModelProperty(value="用法")
        private String usage;
    }
}
