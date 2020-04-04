package com.carrot.lock.redis.spring.boot.lock.impl;

import com.carrot.lock.redis.spring.boot.autoconfigure.CarrotRedisProperties;
import com.carrot.lock.common.builder.KeyBuilder;
import com.carrot.lock.common.exception.AcquireException;
import com.carrot.lock.common.lock.LockAcquirer;
import com.carrot.lock.common.lock.LockReleaser;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * Redis锁获取者实现
 *
 * @author qiyubing
 * @since 1.0
 */
public class LockAcquirerRedisImpl implements LockAcquirer {

    private StringRedisTemplate template;

    private CarrotRedisProperties properties;

    private KeyBuilder keyBuilder;

    public LockAcquirerRedisImpl(StringRedisTemplate template, CarrotRedisProperties properties, KeyBuilder keyBuilder) {
        this.template = template;
        this.properties = properties;
        this.keyBuilder = keyBuilder;
    }

    @Override
    public LockReleaser acquire(String resourceType, String resourceId) throws AcquireException {
        return acquire(resourceType, resourceId, properties.getTimeoutMillis());
    }

    @Override
    public LockReleaser acquire(String resourceType, String resourceId, long timeoutMillis) {
        Duration timeout = Duration.ofMillis(timeoutMillis);
        String key = keyBuilder.build(resourceType, resourceId);
        Boolean success = false;
        while (!success) {
            success = template.opsForValue().setIfAbsent(key, "", timeout);
        }
        return new LockReleaserRedisImpl(template, key);
    }

    @Override
    public LockReleaser acquireOnce(String resourceType, String resourceId) throws AcquireException {
        return acquireOnce(resourceType, resourceId, properties.getTimeoutMillis());
    }

    @Override
    public LockReleaser acquireOnce(String resourceType, String resourceId, long timeoutMillis) throws AcquireException {
        Duration timeout = Duration.ofMillis(timeoutMillis);
        String key = keyBuilder.build(resourceType, resourceId);
        Boolean success = template.opsForValue().setIfAbsent(key, "", timeout);
        if (!success) {
            throw new AcquireException("锁已被占用");
        }
        return new LockReleaserRedisImpl(template, key);
    }

}
