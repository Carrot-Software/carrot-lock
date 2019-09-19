package cn.qiyubing.carrot.redis.spring.boot.lock.impl;

import cn.qiyubing.carrot.common.builder.KeyBuilder;
import cn.qiyubing.carrot.common.exception.AcquireException;
import cn.qiyubing.carrot.common.lock.LockAcquirer;
import cn.qiyubing.carrot.common.lock.LockReleaser;
import cn.qiyubing.carrot.redis.spring.boot.autoconfigure.CarrotRedisProperties;
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
