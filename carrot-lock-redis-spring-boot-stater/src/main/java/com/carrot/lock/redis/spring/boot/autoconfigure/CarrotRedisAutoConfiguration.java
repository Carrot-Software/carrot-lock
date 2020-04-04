package com.carrot.lock.redis.spring.boot.autoconfigure;

import com.carrot.lock.common.builder.KeyBuilder;
import com.carrot.lock.common.lock.LockAcquirer;
import com.carrot.lock.redis.spring.boot.lock.impl.LockAcquirerRedisImpl;
import com.carrot.lock.redis.spring.boot.util.KeyBuilderRedisImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis锁实现自动配置类
 *
 * @author qiyubing
 * @since 1.0
 */
@ConditionalOnClass(StringRedisTemplate.class)
@Configuration
@EnableConfigurationProperties(CarrotRedisProperties.class)
public class CarrotRedisAutoConfiguration {

    private CarrotRedisProperties carrotRedisProperties;

    public CarrotRedisAutoConfiguration(CarrotRedisProperties carrotRedisProperties) {
        this.carrotRedisProperties = carrotRedisProperties;
    }

    @Bean
    @ConditionalOnMissingBean(KeyBuilder.class)
    public KeyBuilder keyBuilderRedisImpl() {
        return new KeyBuilderRedisImpl(carrotRedisProperties.getNamespace());
    }

    @Bean
    public LockAcquirer lockAcquirerRedisImpl(StringRedisTemplate template, CarrotRedisProperties properties, KeyBuilder keyBuilder) {
        return new LockAcquirerRedisImpl(template, properties, keyBuilder);
    }

}
