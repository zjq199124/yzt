package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuVisit;
import com.maizhiyu.yzt.ro.VisitRO;
import com.maizhiyu.yzt.vo.VisitVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 患者随访表(BuVisit)表数据库访问层
 *
 * @author liuyzh
 * @since 2022-12-19 18:56:22
 */
@Mapper
@Repository
public interface BuVisitMapper extends BaseMapper<BuVisit> {
    /**
     * 随访分页列表查询
     *
     * @param page
     * @param query
     * @return
     */
    IPage<VisitVO> getPage(@Param("page") Page page, @Param("query") VisitRO query);

    /**
     * 以随访id获取随访详情
     *
     * @param id
     * @return
     */
    VisitVO getInfoById(Long id);


}
