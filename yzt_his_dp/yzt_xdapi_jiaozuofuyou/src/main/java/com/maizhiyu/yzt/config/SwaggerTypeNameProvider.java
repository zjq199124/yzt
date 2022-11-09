package com.maizhiyu.yzt.config;

import io.swagger.annotations.ApiModel;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.schema.DefaultTypeNameProvider;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 说明：此类用来解决swagger的bug
 * 问题：用@ApiModel注解的，在不同包下或不同类中的内部静态类，类名相同的情况下，swagger无法识别
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER - 100)
public class SwaggerTypeNameProvider extends DefaultTypeNameProvider {

    public static final String SPLIT_CHAR = "$";

    @Override
    public String nameFor(Class<?> type) {
        ApiModel annotation = AnnotationUtils.findAnnotation(type, ApiModel.class);
        if (annotation == null) {
            return super.nameFor(type);
        }
        if (StringUtils.hasText(annotation.value())) {
            return annotation.value();
        }
        // 如果@ApiModel的value为空，则默认取完整类路径
        int packagePathLength = type.getPackage().getName().length();
        return Stream.of(type.getPackage().getName().split("\\."))
                .map(path -> path.substring(0, 1))
                .collect(Collectors.joining(SPLIT_CHAR))
                + SPLIT_CHAR
                + type.getName().substring(packagePathLength + 1);
    }

}