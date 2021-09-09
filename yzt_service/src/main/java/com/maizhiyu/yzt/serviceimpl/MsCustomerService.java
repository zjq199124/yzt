package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.entity.MsCustomer;
import com.maizhiyu.yzt.mapper.HsUserMapper;
import com.maizhiyu.yzt.mapper.MsCustomerMapper;
import com.maizhiyu.yzt.service.IMsCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsCustomerService implements IMsCustomerService {

    @Autowired
    private MsCustomerMapper mapper;

    @Override
    public Integer addCustomer(MsCustomer customer) {
        customer.setStatus(1);
        customer.setCreateTime(new Date());
        customer.setUpdateTime(customer.getCreateTime());
        return mapper.insert(customer);
    }

    @Override
    public Integer delCustomer(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setCustomer(MsCustomer customer) {
        customer.setUpdateTime(new Date());
        return mapper.updateById(customer);
    }

    @Override
    public Map<String, Object> getCustomer(Long id) {
        return mapper.selectCustomer(id);
    }

    @Override
    public List<Map<String, Object>> getCustomerList(Long agencyId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectCustomerList(agencyId, status, term);
        return list;
    }

    @Override
    public List<Map<String, Object>> getCustomerList(String province, String city, String term) {
        List<Map<String, Object>> list = mapper.selectCustomerListByLocation(province, city, term);
        return list;
    }

    @Override
    public List<String> getCustomerTimeslotList(Long id) {
        List<String> list;
        MsCustomer customer = mapper.selectById(id);
        if (customer != null && customer.getTimeslot() != null) {
            list = Arrays.asList(customer.getTimeslot().split(","));
        } else {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getCustomerProvinceList() {
         return mapper.selectCustomerProvinceList();
    }

    @Override
    public List<Map<String, Object>> getCustomerCityList(String province) {
        return mapper.selectCustomerCityList(province);
    }

}
