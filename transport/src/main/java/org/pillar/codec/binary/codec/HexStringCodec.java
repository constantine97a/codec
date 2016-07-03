package org.pillar.codec.binary.codec;

import com.google.common.base.Function;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.Charset;

/**
 * Created by pillar on 2015/8/14.
 * Start域 从Hex字符串至ByteBuf
 * SRC：ByteBuf
 * In:hexString
 */
public class HexStringCodec extends AbstractCodec<ByteBuf, String> implements Codec<ByteBuf, String> {
    private final Charset charset;


    private final Hex hex;


    public HexStringCodec() {
        this.charset = Charsets.ASCII;
        hex = new Hex(charset);
    }

    public HexStringCodec(Charset charset) {
        this.charset = charset;
        hex = new Hex(charset);
    }

    /**
     * @return
     */
    public Function<ByteBuf, String> decoder() {
        return new HexStringDecoder();
    }


    @Override
    public Function<String, ByteBuf> encoder() {
        return new HexStringEncoder();
    }


    public final class HexStringEncoder implements Function<String, ByteBuf> {
        @Override
        public ByteBuf apply(String input) {
            try {
                byte[] bytes = input.getBytes(charset);
                return Unpooled.copiedBuffer(hex.decode(bytes), 0, bytes.length / 2);
            } catch (DecoderException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final class HexStringDecoder implements Function<ByteBuf, String> {
        @Override
        public String apply(ByteBuf buffer) {
            byte[] bytes = new byte[buffer.readableBytes()];
            buffer.readBytes(bytes);
            return new String(hex.encode(bytes));
        }
    }
}
