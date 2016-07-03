package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.UInt8Codec;

import java.text.MessageFormat;

/**
 * Created by pillar on 2015/8/15.
 */
public class VersionPField extends PField {

    private static final int LENGTH = 1;


    public VersionPField(String name, int length) {
        super(name, length);
    }

    public VersionPField(String name) {
        super(name, Integer.class, new UInt8Codec(), LENGTH);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[version,byte]", getName());
    }
}
