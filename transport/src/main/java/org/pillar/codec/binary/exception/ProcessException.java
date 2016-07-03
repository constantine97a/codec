package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/20.
 * 业务处理流程的异常
 */
public class ProcessException extends Exception {
    public ProcessException() {
        super();
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }

    protected ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
