package com.carrot.lock.common.exception;

/**
 * 锁释放异常类
 *
 * @author qiyubing
 * @since 1.0
 */
public class ReleaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReleaseException(String message) {
        super(message);
    }

}