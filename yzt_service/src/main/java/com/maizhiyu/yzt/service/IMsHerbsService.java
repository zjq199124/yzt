package com.maizhiyu.yzt.service;

import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsHerbs;

/**
 * className:IMsHerbsService
 * Package:com.maizhiyu.yzt.service
 * Description:
 *
 * @DATE:2021/12/16 9:21 上午
 * @Author:2101825180@qq.com
 */
public interface IMsHerbsService {
    Integer addMsHerbs(MsHerbs item);

    Integer delMsHerbs(Long id);

    Integer setMsHerbs(MsHerbs item);

    MsHerbs getMsHerbs(Long id);

    PageInfo<MsHerbs> getMsHerbsList(String herbsName, Integer pageNum, Integer pageSize, Long zyId);
}
