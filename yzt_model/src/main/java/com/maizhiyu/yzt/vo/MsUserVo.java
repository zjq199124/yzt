package com.maizhiyu.yzt.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain=true)
public class MsUserVo implements Serializable {

    @ApiModelProperty(value="用户ID")
    private Long id;
    @ApiModelProperty(value="用户状态1正常0停用")
    private Integer status;
    @ApiModelProperty(value="用户名")
    private String userName;
    @ApiModelProperty(value="用户昵称")
    private String nickName;
    @ApiModelProperty(value="用户真名")
    private String realName;
    @ApiModelProperty(value="用户电话")
    private String phone;
    @ApiModelProperty(value="用户性别")
    private Integer sex;
    @ApiModelProperty(value="用户角色列表")
    private List<Long> roleList;
    @ApiModelProperty(value="用户角色展示")
    private  String roleListStr;
    @ApiModelProperty(value="用户部门列表")
    private List<Long> departmentList;


}
