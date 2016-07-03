package org.pillar.codec.binary.convert;

import org.pillar.codec.binary.core.*;

/**
 * Created by pillar on 2015/8/19.
 * 提供BitSet
 */
public class BitSetConverter implements Converter {
    @Override
    public void marshal(Object source, StreamWriter writer, MarshallingContext context) {

    }

    @Override
    public Object unmarshal(StreamReader reader, UnmarshallingContext context) {
        return null;
    }

    @Override
    public boolean canConvert(Class<?> type) {
        return false;
    }
}
