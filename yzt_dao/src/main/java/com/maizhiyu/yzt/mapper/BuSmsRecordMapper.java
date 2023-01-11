package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.entity.BuSmsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface BuSmsRecordMapper extends BaseMapper<BuSmsRecord> {
}
