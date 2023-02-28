package com.maizhiyu.yzt.config;

import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

/**
* @author zjq
* @date 2023-02-28
* @describe redission分布式锁配置类
* */
@Configuration
public class RedissionConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private  String password;

    @Bean
    public RedissonClient redissionClient() {
        Config config = new Config();

        //单机模式
        config.useSingleServer()
                .setAddress("redis://"+host+":"+port)
//                .setPassword(password)
                //将锁的数据放入DB1
                .setDatabase(1);

        //集群模式
        /*config.useClusterServers()
                .addNodeAddress("redis://192.168.56.101:36379")
                .addNodeAddress("redis://192.168.56.102:36379")
                .addNodeAddress("redis://192.168.56.103:36379")
                .setPassword("1111111")
                .setScanInterval(5000);*/

        //哨兵模式
        /*config.useSentinelServers().addSentinelAddress("redis://ip1:port1",
                "redis://ip2:port2",
                "redis://ip3:port3")
                .setMasterName("mymaster")
                .setPassword("password")
                .setDatabase(0);*/

        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


//
//    //使用实例
//    @Autowired
//    RedissonClient redissonClient;
//    //锁头
//    private static final String LOCK_TITLE = "redisLock_";
//
//    @RequestMapping("/redissionTest")
//    public Result redissionTest(String lockKey){
//        //lockKey一般是数据主键,请保证锁名一致性
//        RLock lock = redissonClient.getLock(LOCK_TITLE+lockKey);
//        try{
//            //使线程阻塞，后台会开启定时器每隔设置时间的1/3进行续命操作
//            lock.lock(3, TimeUnit.SECONDS);
//            //业务处理 do something。。。
//        }catch (Exception e) {
//            throw new BusinessException("redission锁异常");
//        }finally {
//            lock.unlock();
//        }
//        return Result.success();
//    }


}
