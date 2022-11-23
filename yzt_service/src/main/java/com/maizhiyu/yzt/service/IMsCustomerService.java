package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.MsCustomer;

import java.util.List;
import java.util.Map;

public interface IMsCustomerService {

    Integer addCustomer(MsCustomer customer);

    Integer delCustomer(Long id);

    Integer setCustomer(MsCustomer customer);

    Map<String, Object> getCustomer(Long id);

    List<Map<String, Object>> getCustomerList(Long agencyId, Integer status, String term);

    List<Map<String, Object>> getCustomerList(String province, String city, String term);

    List<String> getCustomerTimeslotList(Long id);

    List<Map<String, Object>> getCustomerProvinceList();

    List<Map<String, Object>> getCustomerCityList(String province);

    void test();

    MsCustomer getCustomerByName(String customerName);
}
