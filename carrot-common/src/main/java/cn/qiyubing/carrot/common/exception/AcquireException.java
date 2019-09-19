package cn.qiyubing.carrot.common.exception;

/**
 * 锁获取异常类
 *
 * @author qiyubing
 * @since 1.0
 */
public class AcquireException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AcquireException(String message) {
        super(message);
    }

}
