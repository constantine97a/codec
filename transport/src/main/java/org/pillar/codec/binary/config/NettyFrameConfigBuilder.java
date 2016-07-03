package org.pillar.codec.binary.config;

import org.apache.commons.collections.ExtendedProperties;

import java.nio.ByteOrder;

/**
 * Created by pillar on 2015/8/12.
 * NettyFrameConfigBuilder
 */
public class NettyFrameConfigBuilder extends AbastractConfigBuilder {

    public NettyFrameConfig build() {
        ExtendedProperties extendedProperties = this.getProperties();
        int maxFrameLength = extendedProperties.getInt("protocal.frame.maxFrameLength");

        int lengthFieldOffset = extendedProperties.getInt("protocal.frame.lengthFieldOffset", 0);
        int lengthFieldLength = extendedProperties.getInt("protocal.frame.lengthFieldLength", 0);
        int lengthAdjustment = extendedProperties.getInt("protocal.frame.lengthAdjustment", 0);
        int initialBytesToStrip = extendedProperties.getInt("protocal.frame.initialBytesToStrip", 0);
        String strByteOrder = extendedProperties.getString("protocal.frame.byteOrder");
        boolean failFast = extendedProperties.getBoolean("protocal.frame.failFast", true);

        return new NettyFrameConfig(byteOrder(strByteOrder), maxFrameLength, lengthFieldOffset,
                lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }


    private ByteOrder byteOrder(String strByteOrder) {

        if (ByteOrder.BIG_ENDIAN.toString().equals(strByteOrder)) {
            return ByteOrder.BIG_ENDIAN;
        } else if (ByteOrder.LITTLE_ENDIAN.toString().equals(strByteOrder)) {
            return ByteOrder.LITTLE_ENDIAN;
        }
        return ByteOrder.nativeOrder();
    }
}
