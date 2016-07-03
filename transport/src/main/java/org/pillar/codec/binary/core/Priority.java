package org.pillar.codec.binary.core;

/**
 * Created by pillar on 2015/9/29.
 */
public abstract class Priority {
    /**
     * 高优先级
     */
    public static final int PRIORITY_VERY_HIGH = 10000;
    /**
     * 普通优先级
     */
    public static final int PRIORITY_NORMAL = 0;
    /**
     * 低优先级
     */
    public static final int PRIORITY_LOW = -10;
    /**
     * 非常低的优先级
     */
    public static final int PRIORITY_VERY_LOW = -20;
}
