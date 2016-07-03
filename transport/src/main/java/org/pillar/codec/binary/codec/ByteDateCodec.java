package org.pillar.codec.binary.codec;

import org.pillar.codec.binary.util.TransportNettyUtil;
import com.google.common.base.Function;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;

/**
 * Created by pillar on 2015/8/14.
 */
public class ByteDateCodec extends AbstractCodec<ByteBuf, Date> implements Codec<ByteBuf, Date> {

    public static final int DEFAULT_DATA_BYTE_LENGTH = 8;


    @Override
    public Function<ByteBuf, Date> decoder() {
        return new ByteDateDecoder();
    }


    @Override
    public Function<Date, ByteBuf> encoder() {
        return new ByteDateEncoder();
    }

    @Override
    public String toString() {
        return "byteDateCodec{length:" + DEFAULT_DATA_BYTE_LENGTH + "}";
    }

    public final class ByteDateDecoder implements Function<ByteBuf, Date> {

        @Override
        public Date apply(ByteBuf input) {
            if (input.readableBytes() < DEFAULT_DATA_BYTE_LENGTH) {
                throw new IllegalArgumentException("The input ByteBuf is less than " + DEFAULT_DATA_BYTE_LENGTH);
            }
            byte[] bytes = new byte[DEFAULT_DATA_BYTE_LENGTH];
            input.readBytes(bytes);
            return TransportNettyUtil.byteToDate(bytes);
        }


    }

    public final class ByteDateEncoder implements Function<Date, ByteBuf> {
        @Override
        public ByteBuf apply(Date input) {
            return Unpooled.wrappedBuffer(TransportNettyUtil.dateToByte(input));
        }

    }
}
