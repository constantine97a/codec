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
public class UInt32Codec extends AbstractCodec<ByteBuf, Long> implements Codec<ByteBuf, Long> {
    private Logger logger = LoggerFactory.getLogger(UInt16Codec.class);

    public static final int INITIAL_CAPACITY = 4;

    public ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;



    @Override
    public Function<ByteBuf, Long> decoder() {
        return new Function<ByteBuf, Long>() {
            public Long apply(ByteBuf byteBuf) {
                if (byteBuf.readableBytes() < INITIAL_CAPACITY) {
                    throw new IllegalArgumentException("byteBuf readableByte is less than INITIAL_CAPACITY " + INITIAL_CAPACITY);
                }
                byteBuf = byteBuf.order(byteOrder);
                return byteBuf.readUnsignedInt();
            }

        };
    }

    @Override
    public Function<Long, ByteBuf> encoder() {
        return new Function<Long, ByteBuf>() {
            public ByteBuf apply(Long uInt32) {
                if (uInt32 > 0xffffffffL) {
                    throw new IllegalArgumentException("the encoding value:" + uInt32 + " is gt 0xffffffff");
                }
                ByteBuf byteBuf = Unpooled.buffer(INITIAL_CAPACITY, INITIAL_CAPACITY);
                byteBuf = byteBuf.order(byteOrder);
                byteBuf.writeInt(uInt32.intValue());
                return byteBuf;
            }
        };
    }
}
