package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/10.
 */
public class TransportInitException extends TransportException {


    public TransportInitException() {
    }

    public TransportInitException(String message) {
        super(message);
    }

    public TransportInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransportInitException(Throwable cause) {
        super(cause);
    }

    public TransportInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
