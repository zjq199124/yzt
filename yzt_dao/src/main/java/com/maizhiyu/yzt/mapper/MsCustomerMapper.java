package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    IPage<Map<String,Object>> selectCustomerList(
            @Param("page") Page page,
            @Param("agencyId") Long agencyId,
            @Param("status") Integer status,
            @Param("term") String term);

    IPage<Map<String,Object>> selectCustomerListByLocation(
            @Param("page") Page page,
            @Param("province") String province,
            @Param("city") String city,
            @Param("term") String term);

    List<Map<String, Object>> selectCustomerProvinceList();

    List<Map<String, Object>> selectCustomerCityList(
            @Param("province") String province);

}
