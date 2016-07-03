package org.pillar.codec.binary.annotation;

import java.lang.annotation.*;

/**
 * Created by pillar on 2015/11/6.
 * 输出消息体，用于标注输出消息�?
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OutputMB {
    /**
     * 适用的版�?
     *
     * @return
     */
    int version() default 0x80;

    /**
     * 适用的命�?
     *
     * @return
     */
    int command() default 0x00;

    /**
     * �?般情况下是类型名的简�?
     *
     * @return
     */
    String value() default "";
}
