package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.core.Caching;
import org.pillar.codec.binary.reflection.PrioritizedList;
import org.pillar.codec.binary.exception.ConversionException;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by pillar on 2015/8/26.
 */
public class DefaultBuilderLookup implements BuilderLookup, BuilderRegistry, Caching {


    private final PrioritizedList<Builder> builders = new PrioritizedList<>();

    private transient Map<Class<?>, Builder> typeToBuilderMap;


    /**
     * 默认的构造函数
     */
    public DefaultBuilderLookup() {
        readResolve();
    }

    private Object readResolve() {
        // TODO: Use ConcurrentMap
        typeToBuilderMap = Collections.synchronizedMap(new WeakHashMap<Class<?>, Builder>());
        return this;
    }

    @Override
    public Builder lookupBuilderForType(Class<?> type) {
        final Builder cachedConverter = typeToBuilderMap.get(type);
        if (cachedConverter != null) {
            return cachedConverter;
        }
        for (final Builder builder : builders) {
            if (builder.isCompatible(type)) {
                typeToBuilderMap.put(type, builder);
                return builder;
            }
        }
        throw new ConversionException("No builder specified for " + type);
    }

    @Override
    public void registerBuilder(Builder builder, int priority) {

        builders.add(builder, priority);
        for (final Iterator<Class<?>> iter = typeToBuilderMap.keySet().iterator(); iter.hasNext(); ) {
            final Class<?> type = iter.next();
            if (builder.isCompatible(type)) {
                iter.remove();
            }
        }
    }

    @Override
    public void flushCache() {
        typeToBuilderMap.clear();
        for (final Builder builder : builders) {
            if (builder instanceof Caching) {
                ((Caching) builder).flushCache();
            }
        }
    }
}
