package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (SysMultimedia)表实体类
 *
 * @author makejava
 * @since 2022-11-21 15:02:03
 */
@Data
@Accessors(chain = true)
@TableName("sys_multimedia")
@ApiModel(description = "多媒体数据表")
@SuppressWarnings("serial")
public class SysMultimedia {
    /**
     * 主键
     */
    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String name;
    /**
     * 文件类型(1文件，2图片，3音频，4视频）
     */
    @ApiModelProperty(value = "文件类型(1文件，2图片，3音频，4视频")
    private Integer type;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String message;
    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "服务地址")
    private String servicePath;
    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;
    /**
     * 文件后缀
     */
    @ApiModelProperty(value = "文件后缀")
    private String extension;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long size;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "保存类型（0-阿里云公开，1-阿里云私密）")
    private Integer saveType;
    /**
     * 是否删除(0-存在，1-删除)
     */
    @ApiModelProperty(value = "是否删除(0-存在，1-删除)")
    private Integer isDel;
    /**
     * 创建人的id
     */
    @ApiModelProperty(value = "创建人的id")
    private Long creatorId;
    /**
     * 更改人的id
     */
    @ApiModelProperty(value = "更改人的id")
    private Long updateId;

}

