package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/13.
 */
public class CTransportException extends Exception {
    public CTransportException() {
        super();
    }

    public CTransportException(int type) {
        super();
    }

    public CTransportException(int type, String message) {
        super(message);
    }

    public CTransportException(String message) {
        super(message);
    }

    public CTransportException(int type, Throwable cause) {
        super(cause);
    }

    public CTransportException(Throwable cause) {
        super(cause);
    }

    public CTransportException(String message, Throwable cause) {
        super(message, cause);
    }

    public CTransportException(int type, String message, Throwable cause) {
        super(message, cause);
    }
}
