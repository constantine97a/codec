package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/20.
 */
public class HeadProcessException extends ProcessException {

    public HeadProcessException() {
        super();
    }

    public HeadProcessException(String message) {
        super(message);
    }

    public HeadProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeadProcessException(Throwable cause) {
        super(cause);
    }

    protected HeadProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
