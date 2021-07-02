package com.deltaqin.seckill.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author deltaqin
 * @date 2021/6/13 下午11:31
 */

// 自定义线程池（springboot自己不会创建吗）
@Configuration
public class JedisPoolFactory {

    @Autowired
    private RedisConfig redisConfig;

    // 使用线程池配置
    @Bean(name= "jedis.pool")
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config) {
        return new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout()*1000 ,redisConfig.getPassword(), redisConfig.getDatabase());
    }

    // 线程池配置
    @Bean(name= "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig (@Value("${jedis.pool.config.maxTotal}")int maxTotal,
                                            @Value("${jedis.pool.config.maxIdle}")int maxIdle,
                                            @Value("${jedis.pool.config.maxWaitMillis}")int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

}
