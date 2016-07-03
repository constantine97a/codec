package org.pillar.codec.binary.convert;

import org.pillar.codec.binary.core.*;

/**
 * Created by pillar on 2015/8/18.
 */
public abstract class AbstractCollectionConverter implements Converter {
    private final Mapper mapper;

    public AbstractCollectionConverter(final Mapper mapper) {
        this.mapper = mapper;
    }

    protected Mapper getMapper() {
        return mapper;
    }


    @Override
    public abstract void marshal(Object source, StreamWriter writer, MarshallingContext context);

    @Override
    public abstract Object unmarshal(StreamReader reader, UnmarshallingContext context);

    @Override
    public abstract boolean canConvert(Class<?> type);


    protected void writeItem(final Object item, final MarshallingContext context, final StreamWriter writer) {
        Class<?> type = mapper.defaultImplementationOf(writer.getCurrentNodeClazz());
        type = mapper.defaultImplementationOf(type);
        context.convertAnother(item, type);
    }

    protected Object readItem(final StreamReader reader, final UnmarshallingContext context,
                              final Object current) {
        // TODO
        Class<?> type = mapper.defaultImplementationOf(reader.getCurrentNodeClazz());
        type = mapper.defaultImplementationOf(type);
        return context.convertAnother(current, type);
    }

    protected Object createCollection(final Class<?> type) {
        final Class<?> defaultType = getMapper().defaultImplementationOf(type);
        try {
            return defaultType.newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate " + defaultType.getName(), e);
        }
    }
}
