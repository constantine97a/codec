package org.pillar.codec.binary.core;

import org.pillar.codec.binary.exception.ConversionException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/17.
 */
public class Unmarshaller implements UnmarshallingContext {

    private final Logger logger = LoggerFactory.getLogger(Unmarshaller.class);

    private final Stack<Class<?>> types = new Stack<>(1024);

    private StreamReader reader;

    private Class<?> rootClazz;

    private ConverterLookup converterLookup;

    private Mapper mapper;

    private Object root;


    public Unmarshaller(Object root, StreamReader reader, Class<?> clazz, ConverterLookup converterLookup, Mapper mapper) {
        checkNotNull(reader);
        checkNotNull(clazz);
        this.root = root;
        this.reader = reader;
        this.rootClazz = clazz;
        this.converterLookup = converterLookup;
        this.mapper = mapper;
    }


    @Override
    public Object convertAnother(Object parent, Class<?> type) {
        return convertAnother(parent, type, null);
    }


    @Override
    public Object convertAnother(Object parent, Class<?> type, Converter converter) {
        type = mapper.defaultImplementationOf(type);
        if (converter == null) {
            converter = converterLookup.lookupConverterForType(type);
        }
        return convert(parent, type, converter);
    }


    protected Object convert(final Object parent, final Class<?> type, final Converter converter) {
        try {
            types.push(type);
            final Object result = converter.unmarshal(reader, this);
            types.popSilently();
            return result;
        } catch (final Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            throw new ConversionException(e);
        }
    }


    @Override
    public Class<?> getRequiredType() {
        return types.peek();
    }

    @Override
    public void addCompletionCallback(Runnable work, int priority) {

    }

    public Object start() {
        Class type = rootClazz;
        return convertAnother(null, type);
    }


}
