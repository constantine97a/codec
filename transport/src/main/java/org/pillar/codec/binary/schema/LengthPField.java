package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.UInt16Codec;

import java.text.MessageFormat;

/**
 * Created by pillar on 2015/8/15.
 */
public class LengthPField extends PField {

    private static final int LENGTH = 2;


    public LengthPField(String name, int length) {
        super(name, length);
    }

    public LengthPField(String name) {
        this(name, LENGTH);
        this.setCodec(new UInt16Codec());
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[len,byte,byte]", getName());
    }
}
