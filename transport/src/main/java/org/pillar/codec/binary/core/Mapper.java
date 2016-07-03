package org.pillar.codec.binary.core;

/**
 * Created by pillar on 2015/8/18.
 * Find Class
 */
public interface Mapper {


    class Null {}

    Class<?> defaultImplementationOf(Class<?> clazz);
}
