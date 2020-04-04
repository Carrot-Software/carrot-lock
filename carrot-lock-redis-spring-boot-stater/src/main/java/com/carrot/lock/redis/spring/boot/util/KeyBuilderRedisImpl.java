package com.carrot.lock.redis.spring.boot.util;

import com.carrot.lock.common.builder.KeyBuilder;

/**
 * 主键构造器Redis实现
 *
 * @author qiyubing
 * @since 1.0
 */
public class KeyBuilderRedisImpl implements KeyBuilder {

    /**
     * Redis命名空间
     */
    private String namespace;

    public KeyBuilderRedisImpl(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String build(String resourceType, String resourceId) {
        return namespace + "_" + resourceType + "_" + resourceId;
    }

}
