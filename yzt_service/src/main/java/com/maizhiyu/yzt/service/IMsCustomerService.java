package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.MsCustomer;

import java.util.List;
import java.util.Map;

public interface IMsCustomerService extends IService<MsCustomer> {

    Integer addCustomer(MsCustomer customer);

    Integer delCustomer(Long id);

    Integer setCustomer(MsCustomer customer);

    Map<String, Object> getCustomer(Long id);

    IPage<Map<String, Object>> getCustomerList(Page page,Long agencyId, Integer status, String term);

    IPage<Map<String, Object>> getCustomerList(Page page, String province, String city, String term);

    List<String> getCustomerTimeslotList(Long id);

    List<Map<String, Object>> getCustomerProvinceList();

    List<Map<String, Object>> getCustomerCityList(String province);

    void test();

    MsCustomer getCustomerByName(String customerName);
}
