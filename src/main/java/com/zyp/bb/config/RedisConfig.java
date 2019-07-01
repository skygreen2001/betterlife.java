package com.zyp.bb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

/**
 * Created by skygreen on 24/08/2017.
 * [参考:Using Redis with Spring](https://dzone.com/articles/using-redis-spring)
 */

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHostName;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(redisHostName);
        jedisConFactory.setPort(redisPort);
        if (!StringUtils.isEmpty(password)) {
            jedisConFactory.setPassword(password);
        }
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6379);

//        jedisConFactory.setUsePool(true);
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle();
//        jedisPoolConfig.setMinIdle();
//        jedisConFactory.setPoolConfig(jedisPoolConfig);
        return jedisConFactory;
//        return new JedisConnectionFactory();
    }


    @Bean
    RedisTemplate< String, Object > redisTemplate() {
        final RedisTemplate< String, Object > template =  new RedisTemplate< String, Object >();
        template.setConnectionFactory( jedisConnectionFactory() );
        template.setKeySerializer( new StringRedisSerializer() );
//        template.setHashValueSerializer( new GenericToStringSerializer<  >( Object.class ) );
        template.setValueSerializer( new GenericToStringSerializer<  >( Object.class ) );
        return template;
    }

}
