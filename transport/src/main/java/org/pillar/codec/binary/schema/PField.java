package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.Codec;
import org.apache.commons.collections.ListUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pillar on 2015/8/12.
 * 代表协议的域,主要的功能有长度，名称，数据类型由目标提供
 */
public abstract class PField implements Serializable {

    /**
     * 长度
     */
    private int length;
    private String name;
    private PField parent;
    private Class<?> clazz;
    private Codec codec;

    public PField(String name, int length) {
        this.length = length;
        this.name = name;
    }

    /**
     * PField
     *
     * @param name   field
     * @param type   类型
     * @param codec  编码解码器
     * @param length 长度
     */
    public PField(String name, Class<?> type, Codec codec, int length) {
        this.clazz = type;
        this.codec = codec;
        this.name = name;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasChild() {
        return false;
    }

    public List<PField> getChildren() {
        return ListUtils.EMPTY_LIST;
    }

    public int getChildCount() {
        return 0;
    }

    public PField getParent() {
        return parent;
    }

    public void setParent(PField parent) {
        this.parent = parent;
    }

    public PField getChild(int index) {
        return null;
    }

    public PField getLast() {
        return this;
    }

    public void padding(int left) {
        // do nothing
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }


}
