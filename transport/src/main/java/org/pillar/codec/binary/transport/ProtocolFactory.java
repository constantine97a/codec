package org.pillar.codec.binary.transport;


/**
 * Created by pillar on 2015/8/12.
 * Protocol Factory
 * 需要根据协议头中的ComandField获得相应的协议
 */
public interface ProtocolFactory {
    /**
     * 根据类型获取协议
     *
     * @param type
     */
    <T> Protocol getInProtocol(Class<T> type);

    <T> Protocol getOutProtocol(T value, Class<T> type);

}
