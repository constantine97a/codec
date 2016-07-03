package org.pillar.codec.binary.builder;

/**
 * Created by pillar on 2015/8/26.
 */
public interface BuilderRegistry {
    void registerBuilder(Builder converter, int priority);
}
