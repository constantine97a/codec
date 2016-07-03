package org.pillar.codec.binary.codec;

import com.google.common.base.Function;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;

/**
 * Created by Administrator on 2015/8/14.
 */
public class UInt16Codec extends AbstractCodec<ByteBuf, Integer> implements Codec<ByteBuf, Integer> {
    private Logger logger = LoggerFactory.getLogger(UInt16Codec.class);

    public static final int INITIAL_CAPACITY = 2;

    public ByteOrder byteOrder = ByteOrders.byteOrder;


    @Override
    public Function<ByteBuf, Integer> decoder() {
        return new Function<ByteBuf, Integer>() {
            public Integer apply(ByteBuf byteBuf) {
                if (byteBuf.readableBytes() != INITIAL_CAPACITY) {
                    logger.error("UInt16Codec: byteBuf readableByte is not fit the size " + INITIAL_CAPACITY);
                    throw new IllegalArgumentException("UInt16Codec: byteBuf readableByte is not INITIAL_CAPACITY " + INITIAL_CAPACITY);
                }
                byteBuf = byteBuf.order(byteOrder);
                return byteBuf.readUnsignedShort();
            }
        };
    }

    @Override
    public Function<Integer, ByteBuf> encoder() {
        return new Function<Integer, ByteBuf>() {
            public ByteBuf apply(Integer uInt16) {
                if (uInt16 > 0xffff) {
                    logger.error("the encoding value:" + uInt16 + " is gt 0xffff");
                    throw new IllegalArgumentException("the encoding value:" + uInt16 + " is gt 0xffff");
                }
                ByteBuf byteBuf = Unpooled.buffer(INITIAL_CAPACITY, INITIAL_CAPACITY);
                byteBuf = byteBuf.order(byteOrder);
                byteBuf.writeShort(uInt16);
                return byteBuf;
            }
        };
    }
}
