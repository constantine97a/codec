package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.UInt16Codec;

import java.text.MessageFormat;

/**
 * Created by pillar on 2015/8/15.
 */
public class UInt16PField extends PField {
    private static final int LENGTH = 2;

    public UInt16PField(String name) {
        super(name, Integer.class, new UInt16Codec(), LENGTH);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[uint16,byte,byte]", getName());

    }
}
