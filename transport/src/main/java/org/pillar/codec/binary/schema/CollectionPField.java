package org.pillar.codec.binary.schema;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/15.
 * 对应列表
 */
public class CollectionPField extends CompositeField {

    private PField prototype;

    /**
     * @param prototype 内部包含元素
     */
    @Deprecated
    public CollectionPField(String name, PField prototype) {
        super(name, List.class);
        checkNotNull(prototype);
        this.prototype = prototype;
    }

    public CollectionPField(String name, PField prototype, Class<?> type) {
        super(name, type);
        this.prototype = prototype;
    }

    /**
     * In protocol 是知道他们的请求长度的，因此能够使用padding进行扩展
     *
     * @param left left length in ByteBuf
     */
    @Override
    public void padding(int left) {
        int multi = left / this.prototype.getLength();
        for (int i = 0; i < multi; i++) {
            this.addField(prototype);
        }
    }
}
