package org.pillar.codec.binary.builder;

/**
 * Created by pillar on 2015/8/24.
 */
public interface BuilderLookup {
    Builder lookupBuilderForType(Class<?> type);
}
