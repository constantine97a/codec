package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/10.
 */
public class ChecksumInvalidException extends TransportException {

    public ChecksumInvalidException() {
    }

    public ChecksumInvalidException(String message) {
        super(message);
    }

    public ChecksumInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChecksumInvalidException(Throwable cause) {
        super(cause);
    }

    public ChecksumInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
