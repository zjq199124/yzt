package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.BuVisit;
import com.maizhiyu.yzt.ro.VisitRO;
import com.maizhiyu.yzt.vo.VisitVO;

/**
 * 患者随访表(BuVisit)表服务接口层
 *
 * @author liuyzh
 * @since 2022-12-19 18:56:23
 */
public interface BuVisitService extends IService<BuVisit> {
    /**
     * 随访列表数据查询
     *
     * @return
     */
    IPage<VisitVO> getPage(Page page, VisitRO visitRO);

    VisitVO getInfoById(Long id);

}
