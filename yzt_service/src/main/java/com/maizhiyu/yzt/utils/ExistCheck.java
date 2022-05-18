package com.maizhiyu.yzt.utils;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExistChecks.class)
public @interface ExistCheck {

    // 实体类类型
    Class<?> clazz();

    // 数据库字段(支持多个字段查重，使用|分割，如"name|phone")
    String fname();

    // 提示信息
    String message();

}



