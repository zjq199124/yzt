package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.HsCustomerHerbs;
import com.maizhiyu.yzt.entity.MsCustomer;
import com.maizhiyu.yzt.entity.MsHerbs;
import com.maizhiyu.yzt.entity.MsZhongyaoHerbs;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.mapper.HsCustomerHerbsMapper;
import com.maizhiyu.yzt.mapper.MsCustomerMapper;
import com.maizhiyu.yzt.mapper.MsHerbsMapper;
import com.maizhiyu.yzt.mapper.MsZhongyaoHerbsMapper;
import com.maizhiyu.yzt.service.IMsHerbsService;
import com.maizhiyu.yzt.vo.SchZhongyaoHerbsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * className:MsHerbsService
 * Package:com.maizhiyu.yzt.serviceimpl
 * Description:
 *
 * @DATE:2021/12/16 9:22 上午
 * @Author:2101825180@qq.com
 */

@Service
public class MsHerbsService extends ServiceImpl<MsHerbsMapper,MsHerbs> implements IMsHerbsService {

    @Autowired
    private MsHerbsMapper msHerbsMapper;

    @Autowired
    private MsZhongyaoHerbsMapper msZhongyaoHerbsMapper;

    @Autowired
    private MsCustomerMapper mapper;

    @Autowired
    private HsCustomerHerbsMapper hsCustomerHerbsMapper;

    @Override
    public Integer addMsHerbs(MsHerbs ite) {
        int i = msHerbsMapper.insert(ite);
        if(i <= 0) {
            throw new BusinessException("创建失败，请重试");
        }
        List<MsCustomer> msCustomers = mapper.selectList(null);
        for (MsCustomer msCustomer : msCustomers) {
                HsCustomerHerbs item = new HsCustomerHerbs();
                item.setCustomerId(msCustomer.getId());
                item.setInventory(new BigDecimal(0));
                item.setUnitPrice(new BigDecimal(0));
                item.setHerbsId(ite.getId());
                item.setCreateTime(new Date());
                Integer res = hsCustomerHerbsMapper.insert(item);
                if (res <= 0) {
                    throw new BusinessException("创建失败，请重试");
                }
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delMsHerbs(Long id) {
        MsHerbs msHerbs = msHerbsMapper.selectOne(Wrappers.<MsHerbs>lambdaQuery().eq(MsHerbs::getId, id).last(" for update "));
        if(msHerbs == null) {
            throw new BusinessException("删除失败");
        }
        Integer count = msZhongyaoHerbsMapper.selectCount(Wrappers.<MsZhongyaoHerbs>lambdaQuery().eq(MsZhongyaoHerbs::getHerbsId, id));
        if(count > 0) {
            throw new BusinessException("删除失败，当前中药已被加入中药方案中，请先移除中药方案的中药关联");
        }
        return msHerbsMapper.deleteById(id);
    }

    @Override
    public Integer setMsHerbs(MsHerbs item) {
        return msHerbsMapper.updateById(item);
    }

    @Override
    public MsHerbs getMsHerbs(Long id) {
        return msHerbsMapper.selectById(id);
    }

    @Override
    public PageInfo<MsHerbs> getMsHerbsList(String herbsName, Integer pageNum, Integer pageSize,Long zyId) {
        PageHelper.startPage(pageNum, pageSize);
        List<MsHerbs> list1 = msHerbsMapper.selectList(
                Wrappers.<MsHerbs>lambdaQuery().ge(MsHerbs::getFlag,0).like(StringUtils.isNotBlank(herbsName),MsHerbs::getHerbsName,herbsName));

        if(zyId != null) {
            List<SchZhongyaoHerbsVO> list2 = msZhongyaoHerbsMapper.getMsZhongyaoHerbsListBySchZhongyaoId(zyId);
            if(list1 != null && list2 != null) {
                List<Long> collect = list2.stream().map(SchZhongyaoHerbsVO::getHerbsId).collect(Collectors.toList());
                List<MsHerbs> collect1 = list1.stream().filter(item -> !collect.contains(item.getId())).collect(Collectors.toList());
                list1 = collect1;
            }
        }

        PageInfo<MsHerbs> pageInfo = new PageInfo<>(list1, pageSize);
        return pageInfo;
    }
}
