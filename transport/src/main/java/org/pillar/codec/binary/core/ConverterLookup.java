package org.pillar.codec.binary.core;

/**
 * Created by pillar on 2015/8/18.
 */
public interface ConverterLookup {
    Converter lookupConverterForType(Class<?> type);
}
