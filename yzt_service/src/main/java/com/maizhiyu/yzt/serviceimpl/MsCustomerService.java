package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maizhiyu.yzt.entity.HsCustomerHerbs;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.entity.MsCustomer;
import com.maizhiyu.yzt.entity.MsHerbs;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.HsCustomerHerbsMapper;
import com.maizhiyu.yzt.mapper.HsUserMapper;
import com.maizhiyu.yzt.mapper.MsCustomerMapper;
import com.maizhiyu.yzt.mapper.MsHerbsMapper;
import com.maizhiyu.yzt.service.IMsCustomerService;
import com.maizhiyu.yzt.utils.ExistCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
@Transactional(rollbackFor=Exception.class)
public class MsCustomerService implements IMsCustomerService {

    @Autowired
    private MsCustomerMapper mapper;

    @Autowired
    private HsCustomerHerbsMapper hsCustomerHerbsMapper;

    @Autowired
    private MsHerbsMapper msHerbsMapper;


    @Override
    @ExistCheck(clazz = MsCustomer.class, fname = "name", message = "客户已存在")
    public Integer addCustomer(MsCustomer customer) {
        customer.setStatus(1);
        customer.setCreateTime(new Date());
        customer.setUpdateTime(customer.getCreateTime());
        int insert = mapper.insert(customer);
        if(insert > 0) {
            List<MsHerbs> msHerbs = msHerbsMapper.selectList(null);
            for (MsHerbs msHerb : msHerbs) {
                HsCustomerHerbs item = new HsCustomerHerbs();
                item.setCustomerId(customer.getId());
                item.setInventory(new BigDecimal(0));
                item.setUnitPrice(new BigDecimal(0));
                item.setHerbsId(msHerb.getId());
                item.setCreateTime(new Date());
                Integer res = hsCustomerHerbsMapper.insert(item);
                if(res <= 0) {
                    throw new BusinessException("创建失败，请重试");
                }
            }
        }
        return insert;
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

    @Override
    public void test() {
        List<MsCustomer> msCustomers = mapper.selectList(null);
        for (MsCustomer msCustomer : msCustomers) {
            List<MsHerbs> msHerbs = msHerbsMapper.selectList(null);
            for (MsHerbs msHerb : msHerbs) {
                HsCustomerHerbs item = new HsCustomerHerbs();
                item.setCustomerId(msCustomer.getId());
                item.setInventory(new BigDecimal(0));
                item.setUnitPrice(new BigDecimal(0));
                item.setHerbsId(msHerb.getId());
                item.setCreateTime(new Date());
                Integer res = hsCustomerHerbsMapper.insert(item);
                if (res <= 0) {
                    throw new BusinessException("创建失败，请重试");
                }
            }
        }

    }

    @Override
    public MsCustomer getCustomerByName(String customerName) {
        LambdaQueryWrapper<MsCustomer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MsCustomer::getUsername, customerName)
                .eq(MsCustomer::getStatus, 1)
                .orderByDesc(MsCustomer::getUpdateTime)
                .last("limit 1");
        MsCustomer msCustomer = mapper.selectOne(queryWrapper);
        return msCustomer;
    }
}
