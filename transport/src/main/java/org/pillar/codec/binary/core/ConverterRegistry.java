package org.pillar.codec.binary.core;

/**
 * Created by pillar on 2015/8/18.
 */
public interface ConverterRegistry {
    void registerConverter(Converter converter, int priority);
}
