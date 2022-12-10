package com.maizhiyu.yzt.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.maizhiyu.yzt.mapperhis", sqlSessionFactoryRef = "hisSqlSessionFactory")
public class DataSourceHisConfig {

    @Value("${spring.datasource.his.driver-class-name}")
    String driver;

    @Value("${spring.datasource.his.url}")
    String url;

    @Value("${spring.datasource.his.username}")
    String userName;

    @Value("${spring.datasource.his.password}")
    String passWord;

    @Bean(name = "hisDataSource")
    @ConfigurationProperties("spring.datasource.his")
    public DataSource masterDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        return dataSource;
    }

    @Bean(name = "hisSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hisDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper-his/*.xml"));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "hisSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionFactoryTemplate(@Qualifier("hisSqlSessionFactory")SqlSessionFactory sqlSessionFactory ) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
