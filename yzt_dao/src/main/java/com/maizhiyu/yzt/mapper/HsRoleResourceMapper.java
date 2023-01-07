package com.maizhiyu.yzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.entity.HsRoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface HsRoleResourceMapper extends BaseMapper<HsRoleResource> {
    /**
     * 以roleId 查询关联数据（逻辑删除的数据也查)
     *
     * @param roleId
     * @return
     */
    @Select("SELECT * FROM `hs_role_resource` WHERE role_id=#{roleId}")
    List<HsRoleResource> getByRoleId(Long roleId);

    /**
     * 以id恢复逻辑删除数据
     *
     * @param id
     * @return
     */
    @Update("UPDATE `yztdev`.`hs_role_resource` SET `is_del` = 0 WHERE `id` = #{id}")
    Boolean RecoverById(Long id);
}
