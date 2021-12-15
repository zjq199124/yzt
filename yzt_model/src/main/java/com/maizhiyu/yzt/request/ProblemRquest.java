package com.maizhiyu.yzt.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * className:problemRquest
 * Package:com.maizhiyu.yzt.request
 * Description:
 *
 * @DATE:2021/12/13 2:15 下午
 * @Author:2101825180@qq.com
 */

@Data
public class ProblemRquest {

    @ApiModelProperty(value="题目ID")
    private Long tsSytechItemId;

    @ApiModelProperty(value="题目title")
    private String title;
    @ApiModelProperty(value="1.确认完成，2.大致完成, 3.未完成， 4.不能确定需要指导")
    private Integer type;



}
