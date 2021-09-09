package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.MsAgency;
import com.maizhiyu.yzt.entity.MsCustomer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface MsCustomerMapper extends BaseMapper<MsCustomer> {

    Map<String, Object> selectCustomer(
            @Param("id") Long id);

    List<Map<String,Object>> selectCustomerList(
            @Param("agencyId") Long agencyId,
            @Param("status") Integer status,
            @Param("term") String term);

    List<Map<String,Object>> selectCustomerListByLocation(
            @Param("province") String province,
            @Param("city") String city,
            @Param("term") String term);

    List<Map<String, Object>> selectCustomerProvinceList();

    List<Map<String, Object>> selectCustomerCityList(
            @Param("province") String province);

}
