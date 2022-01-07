package com.maizhiyu.yzt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class UploadFilePathConfig extends WebMvcConfigurerAdapter {

//    @Value("${file.static-access-path}")
//    private String staticAccessPath;
//
//    @Value("${file.upload-folder}")
//    private String uploadFolder;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
//    }
}
