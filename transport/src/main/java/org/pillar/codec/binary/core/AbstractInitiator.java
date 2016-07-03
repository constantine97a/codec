package org.pillar.codec.binary.core;

import org.pillar.codec.binary.reflection.ReflectionProvider;
import org.pillar.codec.binary.reflection.SunUnsafeReflectionProvider;
import org.pillar.codec.binary.convert.*;

/**
 * Created by pillar on 2015/8/18.
 */
public abstract class AbstractInitiator {
    public static final int PRIORITY_VERY_HIGH = 10000;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_LOW = -10;
    public static final int PRIORITY_VERY_LOW = -20;
    protected Mapper mapper;
    protected ConverterLookup converterLookup;
    protected ConverterRegistry converterRegistry;
    protected ReflectionProvider reflectionProvider;

    public AbstractInitiator() {
        converterLookup = new DefaultConverterLookup();
        if (converterRegistry == null) {
            converterRegistry = (ConverterRegistry) converterLookup;
        }
        this.mapper = new DefaultImplementationsMapper();
        reflectionProvider = new SunUnsafeReflectionProvider();
    }

    protected void setUpConverter() {
        registerConverter(new ReflectionConverter(mapper, reflectionProvider), PRIORITY_LOW);
        registerConverter(new StringConverter(), PRIORITY_NORMAL);
        registerConverter(new IntConverter(), PRIORITY_NORMAL);
        registerConverter(new DateConverter(), PRIORITY_NORMAL);
        registerConverter(new CollectionConverter(mapper), PRIORITY_NORMAL);
    }

    public void registerConverter(Converter converter, int priority) {
        if (converterRegistry != null) {
            converterRegistry.registerConverter(converter, priority);
        }
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public ConverterLookup getConverterLookup() {
        return converterLookup;
    }

    public void setConverterLookup(ConverterLookup converterLookup) {
        this.converterLookup = converterLookup;
    }

    public ConverterRegistry getConverterRegistry() {
        return converterRegistry;
    }

    public void setConverterRegistry(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    public ReflectionProvider getReflectionProvider() {
        return reflectionProvider;
    }

    public void setReflectionProvider(ReflectionProvider reflectionProvider) {
        this.reflectionProvider = reflectionProvider;
    }
}
