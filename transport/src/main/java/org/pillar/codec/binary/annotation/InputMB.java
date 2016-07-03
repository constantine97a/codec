package org.pillar.codec.binary.annotation;

import java.lang.annotation.*;

/**
 * Created by pillar on 2015/11/6.
 * 消息体的标注，标注输入消息体
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InputMB {
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
     * value will indicate a suggestion name for the message
     * 默认是类型名的简称，如果指定按照指定作为消息体的名称
     *
     * @return String
     */
    String value() default "";

}
