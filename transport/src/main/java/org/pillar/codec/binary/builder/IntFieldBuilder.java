package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.annotation.PF;
import org.pillar.codec.binary.schema.PField;
import org.pillar.codec.binary.schema.UInt16PField;
import org.pillar.codec.binary.schema.UInt32PField;
import org.pillar.codec.binary.schema.UInt8PField;

import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/25.
 * 创建IntField
 */
public class IntFieldBuilder implements Builder<PField> {


    @Override
    public PField build(Object value, Class<?> type, String name, PF meta) {
        if (meta.length() == 8) {
            return new UInt8PField(name);
        } else if (meta.length() == 16) {
            return new UInt16PField(name);
        } else if (meta.length() == 32) {
            return new UInt32PField(name);
        }
        throw new IllegalArgumentException(MessageFormat.format(
                "the name :{0} ,meta:{1}",
                name, meta));
    }


    /**
     * 是否能进行转化
     *
     * @param meta Field meta
     * @param type Field type
     * @return
     */
    @Override
    public boolean isCompatible(Class<?> type) {
        checkNotNull(type);
        return Number.class.isAssignableFrom(type)
                || int.class.equals(type)
                || long.class.equals(type)
                || short.class.equals(type);
    }
}
