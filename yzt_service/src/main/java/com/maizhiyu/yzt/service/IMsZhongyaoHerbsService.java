package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.MsZhongyaoHerbs;
import com.maizhiyu.yzt.vo.SchZhongyaoHerbsVO;

import java.util.List;

/**
 * className:IMsZhongyaoHerbsService
 * Package:com.maizhiyu.yzt.service
 * Description:
 *
 * @DATE:2021/12/17 2:48 下午
 * @Author:2101825180@qq.com
 */
public interface IMsZhongyaoHerbsService {
    List<SchZhongyaoHerbsVO> getMsZhongyaoHerbsListBySchZhongyaoId(Long id);

    Integer removeSchZhongyaoHerbs(Long id);

    void adds(List<MsZhongyaoHerbs> list1);

}
