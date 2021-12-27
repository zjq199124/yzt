package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.HsCustomerHerbs;
import com.maizhiyu.yzt.entity.MsHerbs;
import com.maizhiyu.yzt.mapper.HsCustomerHerbsMapper;
import com.maizhiyu.yzt.mapper.MsHerbsMapper;
import com.maizhiyu.yzt.service.IHsCustomerHerbsService;
import com.maizhiyu.yzt.vo.CustomerHerbsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * className:HsCustomerHerbsService
 * Package:com.maizhiyu.yzt.serviceimpl
 * Description:
 *
 * @DATE:2021/12/16 10:53 上午
 * @Author:2101825180@qq.com
 */

@Service
public class HsCustomerHerbsService implements IHsCustomerHerbsService {

    @Autowired
    private HsCustomerHerbsMapper hsCustomerHerbsMapper;

    @Autowired
    private MsHerbsMapper msHerbsMapper;


    @Override
    public Integer addHsCustomerHerbs(HsCustomerHerbs item) {
        return hsCustomerHerbsMapper.insert(item);
    }

    @Override
    public Integer setHsCustomerHerbs(HsCustomerHerbs item) {
        Long id = item.getId();
        Long customerId = item.getCustomerId();
        item.setId(null);
        item.setCustomerId(null);
        return hsCustomerHerbsMapper.update(item,
                Wrappers.<HsCustomerHerbs>lambdaUpdate()
                        .eq(HsCustomerHerbs::getCustomerId, customerId)
                        .eq(HsCustomerHerbs::getId, id)
        );
    }

    @Override
    public CustomerHerbsVO getHsCustomerHerbs(Long customerId, Long id) {
        HsCustomerHerbs hsCustomerHerbs = hsCustomerHerbsMapper.selectOne(
                Wrappers.<HsCustomerHerbs>lambdaQuery().eq(HsCustomerHerbs::getCustomerId,customerId).eq(HsCustomerHerbs::getId,id));
        MsHerbs msHerbs = msHerbsMapper.selectById(hsCustomerHerbs.getHerbsId());
        CustomerHerbsVO customerHerbsVO = new CustomerHerbsVO();
        BeanUtils.copyProperties(hsCustomerHerbs,customerHerbsVO);
        customerHerbsVO.setHerbsId(msHerbs.getId());
        customerHerbsVO.setUnit(msHerbs.getUnit());
        customerHerbsVO.setHerbsName(msHerbs.getHerbsName());
        return customerHerbsVO;
    }

    @Override
    public PageInfo<CustomerHerbsVO> getHsCustomerHerbsList(Long customerId, String herbsName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MsHerbs> list = msHerbsMapper.selectList(
                Wrappers.<MsHerbs>lambdaQuery().ge(MsHerbs::getFlag,0).like(StringUtils.isNotBlank(herbsName),MsHerbs::getHerbsName,herbsName));
        List<CustomerHerbsVO> list2 = new ArrayList<>();
        if(list != null) {
            List<Long> collect = list.stream().map(MsHerbs::getId).collect(Collectors.toList());
            List<HsCustomerHerbs> hsCustomerHerbsList = hsCustomerHerbsMapper.selectList(
                    Wrappers.<HsCustomerHerbs>lambdaQuery().eq(HsCustomerHerbs::getFlag,0).in(HsCustomerHerbs::getId, collect)
                            .eq(HsCustomerHerbs::getCustomerId, customerId));

            Map<Long, HsCustomerHerbs> maps = new HashMap<>();
            if(hsCustomerHerbsList != null) {
                maps = hsCustomerHerbsList.stream().collect(Collectors.toMap(HsCustomerHerbs::getHerbsId, Function.identity()));
            }

            for (MsHerbs item : list) {
                CustomerHerbsVO customerHerbsVO = new CustomerHerbsVO();
                customerHerbsVO.setHerbsId(item.getId());
                customerHerbsVO.setUnit(item.getUnit());
                customerHerbsVO.setHerbsName(item.getHerbsName());
                HsCustomerHerbs hsCustomerHerbs = maps.get(item.getId());
                if(hsCustomerHerbs != null) {
                    BeanUtils.copyProperties(hsCustomerHerbs,customerHerbsVO);
                }
                list2.add(customerHerbsVO);
            }

        }

        return new PageInfo<>(list2, pageSize);
    }
}
