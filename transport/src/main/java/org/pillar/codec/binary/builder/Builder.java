package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.annotation.PF;

/**
 * Created by pillar on 2015/8/19.
 */
public interface Builder<T> {
    T build(Object value, Class<?> type, String name, PF meta);

    /**
     * 判断是否能进行转化
     *
     * @param meta 元数据
     * @param type Field的类型
     * @return 是否能进行转化
     */
    boolean isCompatible(Class<?> type);
}
