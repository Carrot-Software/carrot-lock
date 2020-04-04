package com.carrot.lock.common.builder;

/**
 * 主键构造器
 *
 * @author qiyubing
 * @since 1.0
 */
public interface KeyBuilder {

    /**
     * 构造主键
     *
     * @param resourceType 资源类型
     * @param resourceId   资源id
     * @return 主键
     */
    String build(String resourceType, String resourceId);

}
