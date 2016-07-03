package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.UInt32Codec;

import java.text.MessageFormat;

/**
 * Created by pillar on 2015/8/15.
 */
public class UInt32PField extends PField {
    private static final int LENGTH = 4;

    public UInt32PField(String name) {
        super(name, Long.TYPE, new UInt32Codec(), LENGTH);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[uint32, {1} byte]", getName(), LENGTH);
    }

}
