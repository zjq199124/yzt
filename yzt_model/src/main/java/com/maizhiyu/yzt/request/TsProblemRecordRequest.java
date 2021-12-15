package com.maizhiyu.yzt.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * className:TsProblemRecordRequest
 * Package:com.maizhiyu.yzt.request
 * Description:
 *
 * @DATE:2021/12/15 1:39 下午
 * @Author:2101825180@qq.com
 */

@Data
public class TsProblemRecordRequest {

    @ApiModelProperty(value="技术考核Id")
    private Long assessId;

    @TableField(exist = false)
    @ApiModelProperty(value="题目列表")
    private List<ProblemRquest> problemList;
}
