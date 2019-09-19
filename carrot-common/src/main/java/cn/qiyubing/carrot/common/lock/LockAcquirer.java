package cn.qiyubing.carrot.common.lock;

import cn.qiyubing.carrot.common.exception.AcquireException;

/**
 * 锁获取者
 *
 * @author qiyubing
 * @since 1.0
 */
public interface LockAcquirer {

    /**
     * 持续获取锁
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @return 锁释放者
     */
    LockReleaser acquire(String resourceType, String resourceId);

    /**
     * 持续获取锁
     *
     * @param resourceType  资源类型
     * @param resourceId    资源ID
     * @param timeoutMillis 锁自动过期时间,单位毫秒
     * @return 锁释放者
     */
    LockReleaser acquire(String resourceType, String resourceId, long timeoutMillis);

    /**
     * 获取一次锁，若不成功则抛出异常
     *
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @return 锁释放者
     * @throws AcquireException 获取锁异常
     */
    default LockReleaser acquireOnce(String resourceType, String resourceId) throws AcquireException {
        throw new AcquireException("This operation is not supported");
    }

    /**
     * 获取一次锁，若不成功则抛出异常
     *
     * @param resourceType  资源类型
     * @param resourceId    资源ID
     * @param timeoutMillis 锁自动过期时间,单位毫秒
     * @return 锁释放者
     * @throws AcquireException 获取锁异常
     */
    default LockReleaser acquireOnce(String resourceType, String resourceId, long timeoutMillis) throws AcquireException {
        throw new AcquireException("This operation is not supported");
    }

}
