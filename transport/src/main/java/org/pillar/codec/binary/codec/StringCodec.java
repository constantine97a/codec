package org.pillar.codec.binary.codec;

import org.pillar.codec.binary.exception.CodecException;
import com.google.common.base.Function;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2015/8/14.
 * bug_fix:padding the left
 */
public class StringCodec extends AbstractCodec<ByteBuf, String> implements Codec<ByteBuf, String> {

    public static final int NULL_CHAR = 0x00;

    private final Logger logger = LoggerFactory.getLogger(StringCodec.class);

    private final Charset charset = Charsets.ASCII;

    private int length;

    public void setLength(int length) {
        this.length = length;
    }

    public StringCodec(int length) {
        this.length = length;
    }


    @Override
    public Function<ByteBuf, String> decoder() {
        return new Function<ByteBuf, String>() {
            @Override
            public String apply(ByteBuf byteBuf) {
                int readableBufSize = byteBuf.readableBytes();
                if (readableBufSize != length) {
                    throw new IllegalArgumentException("the byteBuf length is not fix the predefine size " + length);
                }
                String dumpValue = ByteBufUtil.hexDump(byteBuf);
                ByteBuffer buffer = ByteBuffer.allocate(length);
                byteBuf.readBytes(buffer);
                buffer.flip();
                try {
                    return charset.newDecoder().decode(buffer.asReadOnlyBuffer()).toString().trim();
                } catch (CharacterCodingException e) {
                    logger.error("StringCodec length:{},actual byteBuf size:{},actual value:{}", length, readableBufSize, dumpValue);
                    throw new CodecException(e);
                }
            }
        };

    }

    @Override
    public Function<String, ByteBuf> encoder() {
        return new Function<String, ByteBuf>() {
            @Override
            public ByteBuf apply(String value) {
                ByteBuffer bb = ByteBuffer.allocate(length);
                int padding = length - value.length();
                while (bb.position() < padding) {
                    bb.put((byte) NULL_CHAR);
                }
                charset.newEncoder().encode(CharBuffer.wrap(value), bb, true);
                if (bb.limit() > length) {
                    logger.error("StringCodec length limit:{}, result byteBuf size:{},actual value:{}", length, value.length(), value);
                    throw new CodecException(
                            MessageFormatter.format("the encoding result is {},larger than {}", bb.limit(), length).toString());
                }
                bb.flip();
                return Unpooled.wrappedBuffer(bb);
            }
        };
    }
}
