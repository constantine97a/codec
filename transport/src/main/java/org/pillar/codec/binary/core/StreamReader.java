package org.pillar.codec.binary.core;

import org.pillar.codec.binary.codec.Codec;

import java.io.Closeable;

/**
 * Created by pillar on 2015/8/17.
 * 从Transport读取数据应该是一个层级的 肯定是层级
 */
public interface StreamReader extends Closeable {

    /**
     * @return return {@code true} is the current node have children node
     */
    boolean hasMoreChildren();

    /**
     * read the field value from transport
     *
     * @return ByteBuf
     */
    Object readValue();

    /**
     * move down and reset the current field to unprocessed field
     */
    void moveDown();

    /**
     * move up parent node
     */
    void moveUp();

    /**
     * @return return current pfield of the protocal
     */
    String getCurrentNodeName();


    /**
     * @return get type of the current node
     */
    Class<?> getCurrentNodeClazz();


    /**
     * close the reader
     */
    void close();

    /**
     * return the underlying Reader of the current StreamReader
     *
     * @return StreamReader
     */
    StreamReader underlyingReader();

    /**
     * get codec of the current codec
     *
     * @return
     */
    Codec getCurrentCodec();

}
