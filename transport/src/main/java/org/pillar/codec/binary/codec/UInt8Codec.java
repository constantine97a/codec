package org.pillar.codec.binary.codec;

import com.google.common.base.Function;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by Administrator on 2015/8/14.
 */
public class UInt8Codec extends AbstractCodec<ByteBuf, Integer> implements Codec<ByteBuf, Integer> {

    public static final int INITIAL_CAPACITY = 1;

    @Override
    public Function<ByteBuf, Integer> decoder() {
        return new Function<ByteBuf, Integer>() {
            public Integer apply(ByteBuf byteBuf) {
                if (byteBuf.readableBytes() != INITIAL_CAPACITY) {
                    throw new IllegalArgumentException("UInt8Codec: byteBuf readableByte is "
                            + byteBuf.readableBytes() + ", not INITIAL_CAPACITY " + INITIAL_CAPACITY);
                }
                return (int) byteBuf.readUnsignedByte();
            }
        };
    }

    @Override
    public Function<Integer, ByteBuf> encoder() {
        return new Function<Integer, ByteBuf>() {
            public ByteBuf apply(Integer uInt8) {
                if (uInt8 > 0xff) {
                    throw new IllegalArgumentException("the encoding value:" + uInt8 + " is gt oxff ");
                }
                ByteBuf byteBuf = Unpooled.buffer(INITIAL_CAPACITY, INITIAL_CAPACITY);
                byteBuf.writeByte(uInt8);
                return byteBuf;
            }
        };
    }
}
