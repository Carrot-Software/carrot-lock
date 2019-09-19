package cn.qiyubing.carrot.common;

import cn.qiyubing.carrot.common.exception.AcquireException;
import cn.qiyubing.carrot.common.lock.LockAcquirer;
import cn.qiyubing.carrot.common.lock.LockReleaser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 锁测试工具
 *
 * @author qiyubing
 * @since 1.0
 */
public class LockTester {

    private static Logger log = LoggerFactory.getLogger(LockTester.class);

    private LockAcquirer lockAcquirer;

    private String resourceType = "test-resource-type";

    private String resourceId = "test-resource-id";

    private static int num = 0;

    public LockTester(LockAcquirer lockAcquirer) {
        this.lockAcquirer = lockAcquirer;
    }

    /**
     * 运行测试
     *
     * @param times 每个线程竞争次数，共有三个线程
     * @return 是否成功
     */
    public boolean test(int times) {
        // 并发执行单元
        Runnable task = () -> {
            for (int i = 0; i < times; i++) {
                // 加锁，try-with-resource自动解锁
                try (LockReleaser ignored = lockAcquirer.acquire(resourceType, resourceId)) {
                    // 对静态变量num递增
                    num++;
                } catch (AcquireException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(3);

        // 预期num结果值
        int expectNum = 3 * times;
        log.info("Execute test task, 3 thread, each times = {}, expect num = {}", times, expectNum);
        long start = System.currentTimeMillis();

        // 开始并发
        pool.execute(task);
        pool.execute(task);
        pool.execute(task);

        pool.shutdown();
        while (!pool.isTerminated()) ;

        long end = System.currentTimeMillis();
        String status = num == expectNum ? "success" : "failure";
        log.info("Task done, status = {}, actual num = {}, use {}ms", status, num, end - start);

        return num == expectNum;
    }

}
