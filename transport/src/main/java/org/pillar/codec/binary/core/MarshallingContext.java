package org.pillar.codec.binary.core;


/**
 * Created by pillar on 2015/8/17.
 */
public interface MarshallingContext {

    void convertAnother(Object object, Class<?> clazz);

    void convertAnother(Object object, Class<?> clazz, Converter converter);

    Class<?> getRequiredType();


}
