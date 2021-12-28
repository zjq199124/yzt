package com.example.yzt_ohb.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * className:MybatisPlusConfig
 * Package:com.example.demo.config
 * Description:
 *
 * @DATE:2021/12/23 11:03 上午
 * @Author:2101825180@qq.com
 */

@Configuration
@EnableTransactionManagement
@MapperScan("com.example.yzt_ohb.mapper")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
