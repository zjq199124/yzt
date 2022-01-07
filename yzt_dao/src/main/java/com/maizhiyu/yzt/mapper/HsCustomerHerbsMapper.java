package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsCustomerHerbs;
import com.maizhiyu.yzt.entity.MsHerbs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * className:HsCustomerHerbsMapper
 * Package:com.maizhiyu.yzt.mapper
 * Description:
 *
 * @DATE:2021/12/16 10:56 上午
 * @Author:2101825180@qq.com
 */

@Mapper
@Repository
public interface HsCustomerHerbsMapper  extends BaseMapper<HsCustomerHerbs> {

    int updateDeductionInventory(@Param("customerHerbsId") Long customerHerbsId,@Param("dosage") BigDecimal dosage);
}
