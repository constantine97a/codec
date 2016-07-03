package org.pillar.codec.binary.core;

import org.pillar.codec.binary.codec.Codec;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by pillar on 2015/8/17.
 */
public interface StreamWriter extends Closeable {


    /**
     * @return return {@code true} is the current node have children node
     */
    boolean hasMoreChildren();

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
     * @param value ByteBuf
     */
    void writeValue(Object value);

    /**
     * get target type of the current node
     *
     * @return
     */
    Class<?> getCurrentNodeClazz();

    /**
     * close the writer
     *
     * @throws IOException
     */
    void close() throws IOException;

    /**
     * return the underlying Writer
     *
     * @return
     */
    StreamWriter underlyingWriter();

    /**
     * return the field codec
     *
     * @return get current codec
     */
    Codec getCurrentCodec();

}
