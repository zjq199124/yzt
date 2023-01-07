package com.maizhiyu.yzt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.maizhiyu.yzt.entity.TsAssOperation;
import com.maizhiyu.yzt.entity.TsAssOperationDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@ApiModel(description = "考核成绩")
public class TssAssVO {

    @Data
    @ApiModel
    public static class OperationGradeVO {
        @ApiModelProperty(value = "考核id")
        private Long assId;

        @ApiModelProperty(value = "考核类别项列表")
        private List<OperationDetail> operationDetails;

    }


    @Data
    @ApiModel
    public static class OperationDetail {

        @ApiModelProperty(value="ID")
        private Long id;

        @ApiModelProperty(value="适宜技术ID")
        private Long sytechId;

        @ApiModelProperty(value="具体考核操作ID")
        private Long operationId;

        @ApiModelProperty(value="具体考核操作名称")
        private String operationName;

        @ApiModelProperty(value="总分值")
        private Integer score;

        @ApiModelProperty(value = "操作得分")
        private Integer getOperationScore;


        @ApiModelProperty(value = "考核类别项列表")
        private List<tsAssOperationDetailList> tsAssOperationDetailList;

    }


    @Data
    @ApiModel
    public static class tsAssOperationDetailList extends TsAssOperationDetail{

        @ApiModelProperty(value = "细节得分")
        private Integer getScore;

        @ApiModelProperty(value = "扣分点")
        private String deduct;

    }


}
