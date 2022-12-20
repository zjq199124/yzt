package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuSignature;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 治疗签到表(BuSignature)表数据库访问层
 *
 * @author liuyzh
 * @since 2022-12-19 19:03:08
 */
@Mapper
@Repository
public interface BuSignatureMapper extends BaseMapper<BuSignature>{
 
}
