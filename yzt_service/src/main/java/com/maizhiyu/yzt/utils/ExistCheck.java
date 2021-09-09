package com.maizhiyu.yzt.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistCheck {

    // 实体类类型
    Class<?> clazz();

    // 数据库字段(支持多个字段查重，使用|分割，如"name|phone")
    String fname();

    // 提示信息
    String message();

}
