package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsRoleResource;
import com.maizhiyu.yzt.entity.MsZhongyaoHerbs;
import com.maizhiyu.yzt.vo.SchZhongyaoHerbsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * className:MsZhongyaoHerbsMapper
 * Package:com.maizhiyu.yzt.mapper
 * Description:
 *
 * @DATE:2021/12/17 4:28 下午
 * @Author:2101825180@qq.com
 */

@Mapper
@Repository
public interface MsZhongyaoHerbsMapper extends BaseMapper<MsZhongyaoHerbs> {

    List<SchZhongyaoHerbsVO> getMsZhongyaoHerbsListBySchZhongyaoId(@Param("id") Long id);

    List<SchZhongyaoHerbsVO> getMsZhongyaoHerbsListBySchZhongyaoIds(@Param("ids") List<Long> ids);

    List<SchZhongyaoHerbsVO> getMsZhongyaoHerbsListBySchZhongyaoIdsByCustomerId(@Param("ids") List<Long> ids,@Param("customerId") Long customerId);

}
