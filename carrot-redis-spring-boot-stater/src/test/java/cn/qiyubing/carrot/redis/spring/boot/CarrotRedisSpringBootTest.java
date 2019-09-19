package cn.qiyubing.carrot.redis.spring.boot;

import cn.qiyubing.carrot.common.LockTester;
import cn.qiyubing.carrot.common.lock.LockAcquirer;
import cn.qiyubing.carrot.redis.spring.boot.autoconfigure.CarrotRedisAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qiyubing
 * @since 2019-08-31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrotRedisAutoConfiguration.class)
@EnableAutoConfiguration
public class CarrotRedisSpringBootTest {

    @Autowired
    @Qualifier("lockAcquirerRedisImpl")
    private LockAcquirer lockAcquirer;

    @Test
    public void test() {
        LockTester lockTester = new LockTester(lockAcquirer);
        boolean isSuccess = lockTester.test(3000);
        Assert.assertTrue(isSuccess);
    }
}
