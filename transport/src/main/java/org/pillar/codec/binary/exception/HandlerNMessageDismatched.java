package org.pillar.codec.binary.exception;

/**
 * Created by pillar on 2015/8/10.
 * 处理器和消息的内容不匹配
 */
public class HandlerNMessageDismatched extends TransportException {

    /**
     * handler的类型
     */
    private Class<?> handlerClazz;
    /**
     * message 的类型
     */
    private Class<?> messageClazz;

    public HandlerNMessageDismatched(Class<?> handlerClazz, Class<?> messageClazz) {
        this.handlerClazz = handlerClazz;
        this.messageClazz = messageClazz;
    }

    public HandlerNMessageDismatched(String message, Class<?> handlerClazz, Class<?> messageClazz) {
        super(message);
        this.handlerClazz = handlerClazz;
        this.messageClazz = messageClazz;
    }

    public HandlerNMessageDismatched(String message, Throwable cause, Class<?> handlerClazz, Class<?> messageClazz) {
        super(message, cause);
        this.handlerClazz = handlerClazz;
        this.messageClazz = messageClazz;
    }

    public HandlerNMessageDismatched(Throwable cause, Class<?> handlerClazz, Class<?> messageClazz) {
        super(cause);
        this.handlerClazz = handlerClazz;
        this.messageClazz = messageClazz;
    }

    public HandlerNMessageDismatched(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Class<?> handlerClazz, Class<?> messageClazz) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.handlerClazz = handlerClazz;
        this.messageClazz = messageClazz;
    }
}
