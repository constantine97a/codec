package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.HexStringCodec;

import java.text.MessageFormat;

/**
 * Created by pillar on 2015/8/15.
 */
public class StartPField extends PField {

    private static final int LENGTH = 2;

    public StartPField(String name, int length) {
        super(name, length);
    }

    public StartPField(String name) {
        this(name, LENGTH);
        this.setCodec(new HexStringCodec());
    }


    @Override
    public String toString() {
        return MessageFormat.format("{0}[start,byte,byte]", getName());
    }
}
