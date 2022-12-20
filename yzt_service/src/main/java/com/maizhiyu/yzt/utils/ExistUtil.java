package com.maizhiyu.yzt.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.exception.BusinessException;

public class ExistUtil {

    /**
     *
     * 判断数据是否重复
     * [已经用注解的方式实现了，这个方法暂时不需要了]
     *
     * @param t 实体类
     * @param mapper 实体类的mapper
     * @param field 字段名
     * @param value 字段值
     * @param message 提示信息
     * @param <T>
     * @return
     */
    public static <T> boolean checkExist(BaseMapper mapper, T t, String field, Object value, String message) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(field, value);
        Long count = mapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(message);
        }
        return false;
    }

}
