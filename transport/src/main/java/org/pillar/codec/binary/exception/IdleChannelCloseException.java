package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/10.
 */
public class IdleChannelCloseException extends TransportException {

    public IdleChannelCloseException() {
    }

    public IdleChannelCloseException(String message) {
        super(message);
    }

    public IdleChannelCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdleChannelCloseException(Throwable cause) {
        super(cause);
    }

    public IdleChannelCloseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
