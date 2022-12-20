package com.maizhiyu.yzt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 焦作妇幼his-云平台-疾病映射表
 * </p>
 *
 * @author zhangxiansho
 * @since 2022-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DiseaseMapping extends Model<DiseaseMapping> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 对应云平台dict_disease表中的id
     */
    private Long diseaseId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 缩写
     */
    private String abbr;

    /**
     * his内部编码
     */
    private String hisCode;

    /**
     * his内名称
     */
    private String hisName;

    /**
     * 状态：1：已删除；0：未删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;


//    @Override
//    protected Serializable pkVal() {
//        return this.id;
//    }

}
