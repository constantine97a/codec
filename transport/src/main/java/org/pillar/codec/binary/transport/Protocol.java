package org.pillar.codec.binary.transport;

import org.pillar.codec.binary.schema.CompositeField;
import org.pillar.codec.binary.schema.PField;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/12.
 * 代表二进制的协议
 * <p/>
 * 协议和子协议中中Field 如 通过Composite 指导如何解析,并关联解析后的数据
 */
public class Protocol {

    private Class<?> rootType;

    private CompositeField cf;

    private PField currrentField;


    public Protocol(CompositeField field) {
        checkNotNull(field);
        // TODO 应该简化接口 让 type 从Field中获取
        this.rootType = field.getClazz();
        this.cf = field;
        initCurrentField();
    }

    /**
     * padding
     *
     * @param length
     */
    public Protocol padding(int length) {
        if (this.cf.getLength() >= length) {
            return this;
        }
        for (PField f : this.cf) {
            f.padding(length - this.cf.getLength());
        }
        return this;
    }


    protected void initCurrentField() {
        currrentField = this.cf;
    }

    public Class<?> getType() {
        return rootType;
    }

    public void setType(Class<?> type) {
        this.rootType = type;
    }

    public CompositeField getCf() {
        return cf;
    }

    public void setCf(CompositeField cf) {
        this.cf = cf;
    }

    public PField currentField() {
        return this.currrentField;
    }


}
