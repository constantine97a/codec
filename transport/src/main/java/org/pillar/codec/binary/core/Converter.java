package org.pillar.codec.binary.core;


public interface Converter {

    void marshal(Object source, StreamWriter writer, MarshallingContext context);

    Object unmarshal(StreamReader reader, UnmarshallingContext context);

    boolean canConvert(Class<?> type);

}
