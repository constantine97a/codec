package org.pillar.codec.binary.convert;

import org.pillar.codec.binary.core.Caching;
import org.pillar.codec.binary.core.Converter;
import org.pillar.codec.binary.core.ConverterLookup;
import org.pillar.codec.binary.core.ConverterRegistry;
import org.pillar.codec.binary.exception.ConversionException;
import org.pillar.codec.binary.reflection.PrioritizedList;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by pillar on 2015/8/18.
 */
public class DefaultConverterLookup implements ConverterLookup, ConverterRegistry, Caching {

    private final PrioritizedList<Converter> converters = new PrioritizedList<>();

    private transient Map<Class<?>, Converter> typeToConverterMap;

    public DefaultConverterLookup() {
        readResolve();
    }


    public Converter lookupConverterForType(final Class<?> type) {
        final Converter cachedConverter = typeToConverterMap.get(type);
        if (cachedConverter != null) {
            return cachedConverter;
        }
        for (final Converter converter : converters) {
            if (converter.canConvert(type)) {
                typeToConverterMap.put(type, converter);
                return converter;
            }
        }
        throw new ConversionException("No converter specified for " + type);
    }


    public void registerConverter(final Converter converter, final int priority) {
        converters.add(converter, priority);
        for (final Iterator<Class<?>> iter = typeToConverterMap.keySet().iterator(); iter.hasNext(); ) {
            final Class<?> type = iter.next();
            if (converter.canConvert(type)) {
                iter.remove();
            }
        }
    }


    private Object readResolve() {
        // TODO: Use ConcurrentMap
        typeToConverterMap = Collections.synchronizedMap(new WeakHashMap<Class<?>, Converter>());
        return this;
    }

    @Override
    public void flushCache() {
        typeToConverterMap.clear();
        for (final Converter converter : converters) {
            if (converter instanceof Caching) {
                ((Caching) converter).flushCache();
            }
        }
    }
}
