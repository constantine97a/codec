package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.annotation.PF;
import org.pillar.codec.binary.schema.DatePField;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by pillar on 2015/8/26.
 */
public class DateFieldBuilder implements Builder<DatePField> {

    @Override
    public DatePField build(Object value, Class<?> type, String name, PF meta) {
        if (StringUtils.isBlank(name)) {
            name = StringUtils.uncapitalize(type.getSimpleName());
        }
        return new DatePField(name, meta.length());
    }

    @Override
    public boolean isCompatible(Class<?> type) {
        return Date.class.isAssignableFrom(type);
    }
}
