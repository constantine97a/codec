package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.annotation.PF;
import org.pillar.codec.binary.schema.StringPField;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/25.
 */
public class StringFieldBuilder implements Builder<StringPField> {
    /**
     * create StringPField for string field
     *
     * @param value string value
     * @param type  string type
     * @param name  fieldName
     * @param meta  meta
     * @return StringPField
     */
    @Override
    public StringPField build(Object value, Class<?> type, String name, PF meta) {
        return new StringPField(name, meta.length());
    }

    @Override
    public boolean isCompatible(Class<?> type) {
        checkNotNull(type);
        return String.class.isAssignableFrom(type);
    }
}
