package org.pillar.codec.binary.config;

import com.google.inject.ProvidedBy;

import java.nio.ByteOrder;

/**
 * Created by pillar on 2015/8/12.
 */

@ProvidedBy(DefaultNettyFrameConfigProvider.class)
public class NettyFrameConfig {

    private ByteOrder lengthFieldByteOrder = ByteOrder.LITTLE_ENDIAN;
    private final int maxFrameLength;
    private final int lengthFieldOffset;
    private final int lengthFieldLength;
    private final int lengthAdjustment;
    private final int initialBytesToStrip;
    private boolean failFast = false;

    public NettyFrameConfig(int maxFrameLength,
                            int lengthFieldOffset,
                            int lengthFieldLength,
                            int lengthAdjustment,
                            int initialBytesToStrip) {
        this.maxFrameLength = maxFrameLength;
        this.lengthFieldOffset = lengthFieldOffset;
        this.lengthFieldLength = lengthFieldLength;
        this.lengthAdjustment = lengthAdjustment;
        this.initialBytesToStrip = initialBytesToStrip;
    }

    public NettyFrameConfig(ByteOrder lengthFieldByteOrder,
                            int maxFrameLength,
                            int lengthFieldOffset,
                            int lengthFieldLength,
                            int lengthAdjustment,
                            int initialBytesToStrip,
                            boolean failFast) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.lengthFieldByteOrder = lengthFieldByteOrder;
        this.failFast = failFast;
    }

    public ByteOrder getLengthFieldByteOrder() {
        return lengthFieldByteOrder;
    }

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public int getLengthFieldOffset() {
        return lengthFieldOffset;
    }

    public int getLengthFieldLength() {
        return lengthFieldLength;
    }

    public int getLengthAdjustment() {
        return lengthAdjustment;
    }

    public int getInitialBytesToStrip() {
        return initialBytesToStrip;
    }

    public boolean isFailFast() {
        return failFast;
    }

    /**
     *
     * @return
     */
    public static NettyFrameConfigBuilder newBuilder() {
        return new NettyFrameConfigBuilder();
    }

    @Override
    public String toString() {
        return "NettyFrameConfig{" +
                "lengthFieldByteOrder=" + lengthFieldByteOrder +
                ", maxFrameLength=" + maxFrameLength +
                ", lengthFieldOffset=" + lengthFieldOffset +
                ", lengthFieldLength=" + lengthFieldLength +
                ", lengthAdjustment=" + lengthAdjustment +
                ", initialBytesToStrip=" + initialBytesToStrip +
                ", failFast=" + failFast +
                '}';
    }
}
