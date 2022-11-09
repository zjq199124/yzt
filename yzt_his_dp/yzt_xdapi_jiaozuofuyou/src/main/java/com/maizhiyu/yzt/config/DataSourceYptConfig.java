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
@MapperScan(basePackages = "com.maizhiyu.yzt.mapperypt", sqlSessionFactoryRef = "yptSqlSessionFactory")
public class DataSourceYptConfig {

    @Value("${spring.datasource.ypt.driver-class-name}")
    String driver;

    @Value("${spring.datasource.ypt.url}")
    String url;

    @Value("${spring.datasource.ypt.username}")
    String userName;

    @Value("${spring.datasource.ypt.password}")
    String passWord;

    @Bean(name = "yptDataSource")
    @ConfigurationProperties("spring.datasource.ypt")
    public DataSource masterDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        return dataSource;
    }

    @Bean(name = "yptSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("yptDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper-ypt/*.xml"));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "yptSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionFactoryTemplate(@Qualifier("yptSqlSessionFactory")SqlSessionFactory sqlSessionFactory ) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
