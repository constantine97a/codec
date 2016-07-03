package org.pillar.codec.binary.core;


/**
 * Created by pillar on 2015/8/17.
 */
public interface UnmarshallingContext {

    Object convertAnother(Object current, Class<?> type);

    Object convertAnother(Object current, Class<?> type, Converter converter);

    Class<?> getRequiredType();

    void addCompletionCallback(Runnable work, int priority);


}
