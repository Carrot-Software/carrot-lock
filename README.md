# Carrot Lock,一款非常优雅的分布式锁框架

## 命名由来

中国有句俗语，一个萝卜一个坑，用来形容分布锁再合适不过了，所以项目命名为Carrot。

## 用法

1. 在`application.yml`中完成相关配置
2. 注入`LockAcquirer`，并可以通过`@Qualifier`来指定锁的实现类

~~~java
@Autowired
LockAcquirer lockAcquirer;
~~~
3. 获取锁，执行业务逻辑

~~~java
try (LockReleaser ignored = lockAcquirer.acquire(resourceType, resourceId)) {
	// Do something in lock
} catch (AcquireException e) {
	e.printStackTrace();
}
~~~

## 优点
1. 简洁、优雅
2. 利用`try-with-resource`实现自动解锁
3. 具有良好的扩展性，可基于项目框架添加自定义分布式锁实现
4. 无缝切换，可以通过`@Qualifier`注解，随时选择使用不同的锁实现

## 完成度

1. 项目框架已成型
2. 完成基于Redis的分布式锁实现`carrot-redis-spring-boot-stater`。

## 未来规划

1. 逐步优化性能
2. 将会加入基于`Zookeeper`的分布式锁实现
3. 会尝试加入更多的锁风格
4. 提供更多版本支持，如：`非Spring`、`Spring`、`Spring Boot`

## 核心设计

1. `LockAcquirer`用来获取锁并返回`LockReleaser`
2. `LockReleaser`用来解锁，若放入`try-with-resource`中则可自动解锁

### 锁获取者接口

~~~java
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
~~~

### 锁释放者接口

~~~java
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
~~~

### 主键构造器接口

~~~java
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
~~~

## 配置项

### Carrot Redis Spring Boot

| 配置项                      | 作用                          |
| --------------------------- | ----------------------------- |
| carrot.redis.namespace      | Redis命名空间                 |
| carrot.redis.timeout-millis | Redis锁自动过期时间，单位毫秒 |
| spring.redis.*              | Redis连接配置                 |

## 扩展点

1. 将自定义的`KeyBuilder`实现加入Spring容器，从而替换默认的主键构造器。
2. 实现`LockAcquirer`以及`LockReleaser`，并将其加入Spring容器，从而添加自定义的锁实现。

## 开源共建

由于本人还是一名在校大二学生，并正处于一个电商项目开发阶段，由于时间、技术等各方面因素可能近期没有时间去维护，欢迎大家共同完善Carrot!
