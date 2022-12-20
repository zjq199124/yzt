package com.maizhiyu.yzt.utils;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizhiyu.yzt.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


@Aspect
@Component
public class ExistCheckAspect {


    // 单个 ExistCheck
    @Pointcut(value = "@annotation(com.maizhiyu.yzt.utils.ExistCheck)")
    public void access1() {
    }


    // 多个 ExistCheck
    @Pointcut(value = "@annotation(com.maizhiyu.yzt.utils.ExistChecks)")
    public void access2() {
    }


    @Before("access1() && @annotation(existCheck)")
    public void doBefore(JoinPoint joinPoint, ExistCheck existCheck) throws Throwable {
        processBefore(joinPoint, existCheck);
    }


    @Before("access2() && @annotation(existChecks)")
    public void doBefore(JoinPoint joinPoint, ExistChecks existChecks) throws Throwable {
        for (ExistCheck existCheck : existChecks.value()) {
            processBefore(joinPoint, existCheck);
        }
    }


    // 处理
    private void processBefore(JoinPoint joinPoint, ExistCheck existCheck) throws Throwable {
        // 获取注解参数值
        Class<?> clazz = existCheck.clazz();
        String fname = existCheck.fname();
        String message = existCheck.message();

        // 生成Mapper类名称
        String entityClassName = clazz.getSimpleName();
        String mapperClassName = entityClassName + "Mapper";

        // 获取被代理的对象
        Object target = joinPoint.getTarget();

        // 获取属性列表查找mapper对象
        Field fields[] = target.getClass().getDeclaredFields();
        for (Field field : fields) {

            // 找到mapper对象
            if (field.getType().getName().contains(mapperClassName)) {

                // 获取mapper
                field.setAccessible(true);
                BaseMapper mapper = (BaseMapper) field.get(target);

                // 创建查询条件
                QueryWrapper wrapper = new QueryWrapper();

                // 获取参数列表
                for (Object arg : joinPoint.getArgs()) {
                    // 参数类型为实体类型时
                    if (arg.getClass().getSimpleName().equals(entityClassName)) {
                        // 取出fname的值
                        for (String key : fname.split("\\|")) {
                            String mname;
                            if (!key.contains("_")) {
                                mname = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
                            } else {
                                mname = underlineToCamel("get_" + key);
                            }
                            Method method = arg.getClass().getMethod(mname);
                            Object val = method.invoke(arg);
                            if (val == null) {
                                wrapper.isNull(key);
                            } else {
                                wrapper.eq(key, val);
                            }
                            System.out.println(key + " : " + val);;
                        }
                    }
                }

                // 查询数据库
                Long count = mapper.selectCount(wrapper);

                // 数据重复(抛出异常)
                if (count > 0) {
                    throw new BusinessException(message);
                }

                // 不重复(什么也不做)
                else {
                }
            }
        }
    }


    //驼峰转下划线
    public static String camelToUnderline(String param, Integer charType) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_');
            }
            if (charType == 2) {
                sb.append(Character.toUpperCase(c));  //统一都转大写
            } else {
                sb.append(Character.toLowerCase(c));  //统一都转小写
            }


        }
        return sb.toString();
    }


    //下划线转驼峰
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        Boolean flag = false; // "_" 后转大写标志,默认字符前面没有"_"
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == '_') {
                flag = true;
                continue;   //标志设置为true,跳过
            } else {
                if (flag == true) {
                    //表示当前字符前面是"_" ,当前字符转大写
                    sb.append(Character.toUpperCase(param.charAt(i)));
                    flag = false;  //重置标识
                } else {
                    sb.append(Character.toLowerCase(param.charAt(i)));
                }
            }
        }
        return sb.toString();
    }

}
