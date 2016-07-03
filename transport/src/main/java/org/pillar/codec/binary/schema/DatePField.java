package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.ByteDateCodec;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by pillar on 2015/8/15.
 */
public class DatePField extends PField {
    private static final int LENGTH = 8;

    public DatePField(String name) {
        super(name, Date.class, new ByteDateCodec(), LENGTH);
    }

    public DatePField(String name, int length) {
        super(name, Date.class, new ByteDateCodec(), length);
    }


    @Override
    public String toString() {
        return MessageFormat.format("{0}[date,byte,byte,byte,byte,byte,byte,byte,byte]", getName());
    }
}
