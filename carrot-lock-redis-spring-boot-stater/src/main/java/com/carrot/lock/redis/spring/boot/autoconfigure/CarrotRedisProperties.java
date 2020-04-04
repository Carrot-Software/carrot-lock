package com.carrot.lock.redis.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis锁实现配置类
 *
 * @author qiyubing
 * @since 1.0
 */
@ConfigurationProperties(prefix = "carrot.lock.redis")
public class CarrotRedisProperties {

    /**
     * Redis命名空间
     */
    private String namespace = "carrot_lock_test";

    /**
     * 锁自动过期时间,单位毫秒
     */
    private Long timeoutMillis = 10000L;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(Long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
}
