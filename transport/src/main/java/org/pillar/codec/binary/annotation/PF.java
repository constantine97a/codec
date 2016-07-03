package org.pillar.codec.binary.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pillar on 2015/8/25.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PF {

    /**
     * 目标的类�?
     *
     * @return 目标类型
     */
    Class<?> type() default NULL.class;


    Class<?> item() default NULL.class;


    /**
     * 顺序 默认顺序是自然顺�?
     *
     * @return int Order
     */
    int order() default 0;

    /**
     * 表示Field的长�?
     *
     * @return 长度
     */
    int length() default 0;

    /**
     * 字符集合
     *
     * @return charset string
     */
    String charset() default "US-ASCII";

    /**
     * 解码的ByteOrder
     *
     * @return byteOrder string
     */
    String byteOrder() default "LITTLE_ENDIAN";
}
