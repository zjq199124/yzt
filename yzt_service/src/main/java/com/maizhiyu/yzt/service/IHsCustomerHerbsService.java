package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.HsCustomerHerbs;
import com.maizhiyu.yzt.vo.CustomerHerbsVO;

/**
 * className:IHsCustomerHerbsService
 * Package:com.maizhiyu.yzt.service
 * Description:
 *
 * @DATE:2021/12/16 10:53 上午
 * @Author:2101825180@qq.com
 */
public interface IHsCustomerHerbsService extends IService<HsCustomerHerbs> {
    Integer addHsCustomerHerbs(HsCustomerHerbs item);

    Integer setHsCustomerHerbs(HsCustomerHerbs item);

    CustomerHerbsVO getHsCustomerHerbs(Long customerId,Long id);

    PageInfo<CustomerHerbsVO> getHsCustomerHerbsList(Long customerId,String herbsName, Integer pageNum, Integer pageSize);
}
