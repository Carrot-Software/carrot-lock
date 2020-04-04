package com.carrot.lock.common.lock;

/**
 * 锁释放者
 *
 * @author qiyubing
 * @since 1.0
 */
public interface LockReleaser extends AutoCloseable {

    /**
     * 释放锁
     */
    void release();

    /**
     * override AutoCloseable close()
     */
    @Override
    default void close() {
        release();
    }

}
