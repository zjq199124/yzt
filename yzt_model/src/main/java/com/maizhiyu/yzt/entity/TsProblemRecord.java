package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maizhiyu.yzt.request.ProblemRquest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * className:TsProblemRecord
 * Package:com.maizhiyu.yzt.entity
 * Description:
 *
 * @DATE:2021/12/13 11:50 上午
 * @Author:2101825180@qq.com
 */
@Data
@Accessors(chain=true)
@TableName("ts_problem_record")
@ApiModel(description="答题记录表")
public class TsProblemRecord {

    @ApiModelProperty(value="ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    private String problemJson;

    @ApiModelProperty(value="分数成绩")
    private Integer results;

    @ApiModelProperty(value="a.优秀 b.良好 c.差 d.不及格")
    private String evaluation;

    private String passConditionJson;

    @ApiModelProperty(value="提交的时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date time;

    @ApiModelProperty(value="技术考核Id")
    private Long assessId;

    @TableField(exist = false)
    @ApiModelProperty(value="题目列表")
    private List<ProblemRquest> problemList;

    @TableField(exist = false)
    @ApiModelProperty(value="试卷")
    private MsExaminationPaper msExaminationPaper;


}
