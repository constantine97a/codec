package org.pillar.codec.binary.codec;

import com.google.common.base.Strings;

import java.nio.ByteOrder;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Administrator on 2015/8/14.
 */
public class ByteOrders {
    public static final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;


    public ByteOrder getByteOrder(String value) {
        checkArgument(!Strings.isNullOrEmpty(value));
        if (ByteOrder.LITTLE_ENDIAN.toString().equals(value)) {
            return ByteOrder.LITTLE_ENDIAN;
        } else {
            return ByteOrder.BIG_ENDIAN;
        }
    }
}
