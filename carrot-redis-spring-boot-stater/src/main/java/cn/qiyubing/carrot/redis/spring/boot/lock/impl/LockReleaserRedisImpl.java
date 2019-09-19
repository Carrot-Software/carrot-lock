package cn.qiyubing.carrot.redis.spring.boot.lock.impl;

import cn.qiyubing.carrot.common.lock.LockReleaser;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis锁释放者实现
 *
 * @author qiyubing
 * @since 1.0
 */
public class LockReleaserRedisImpl implements LockReleaser {

    private StringRedisTemplate template;

    private String key;

    public LockReleaserRedisImpl(StringRedisTemplate template, String key) {
        this.template = template;
        this.key = key;
    }

    @Override
    public void release() {
        template.delete(key);
    }

}
