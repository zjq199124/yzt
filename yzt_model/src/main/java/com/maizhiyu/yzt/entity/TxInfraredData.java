package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;

/**
 * (TxInfraredData)实体类
 *
 * @author makejava
 * @since 2022-11-21 19:27:11
 */
@Data
@TableName("tx_infrared_data")
@Accessors(chain = true)
@ApiModel(description = "红外检测数据表")
public class TxInfraredData implements Serializable {
    private static final long serialVersionUID = 928517531069155581L;
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户电话号码
     */
    private String phone;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 性别（0女性，1男性）
     */
    private Integer sex;
    /**
     * 用户身份证号码
     */
    private String idCard;
    /**
     * 检测时间
     */
    private Date testDate;
    /**
     * 望
     */
    private String watch;
    /**
     * 闻
     */
    private String listen;
    /**
     * 问
     */
    private String ask;
    /**
     * 切
     */
    private String feel;
    /**
     * 对称性
     */
    private String duichenxing;
    /**
     * 规律性
     */
    private String guilvxing;
    /**
     * 辨寒热
     */
    private String bianhanre;
    /**
     * 图像特征
     */
    private String tuxiangtezheng;
    /**
     * 体质量表
     */
    private String tizhiliangbiao;
    /**
     * 热态分布
     */
    private String retaifenbu;
    /**
     * 中医体质
     */
    private String zhongyitizhi;
    /**
     * 脏腑
     */
    private String zangfu;
    /**
     * 三焦
     */
    private String sanjiao;
    /**
     * 经络
     */
    private String jingluo;
    /**
     * 解剖分区
     */
    private String jiepoufenqu;
    /**
     * 六经皮部
     */
    private String liujingpibu;
    /**
     * 开窍其华
     */
    private String kaiqiaoqihua;
    /**
     * 五脏色部
     */
    private String wuzangsebu;
    /**
     * 头部对称
     */
    private String toubuduichen;
    /**
     * 主证
     */
    private String zhuzheng;
    /**
     * 兼证
     */
    private String jianzheng;
    /**
     * 调理原则
     */
    private String tiaoliyuanze;
    /**
     * 成药
     */
    private String chenyao;
    /**
     * 穴位
     */
    private String xuewei;
    /**
     * 饮食
     */
    private String yinshi;
    /**
     * 营养保健品
     */
    private String yingyangbaojianpin;

    /**
     * 对应sys_mulitimedia表的ID
     */
    private Long multimediaId;

    /**
     * 是否删除 0-否，1-是
     */
    private int isDel;


}

