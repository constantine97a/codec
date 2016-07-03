package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/19.
 */
public class CodecException extends RuntimeException {
    public CodecException() {
        super();
    }

    public CodecException(String message) {
        super(message);
    }

    public CodecException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodecException(Throwable cause) {
        super(cause);
    }

    protected CodecException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
