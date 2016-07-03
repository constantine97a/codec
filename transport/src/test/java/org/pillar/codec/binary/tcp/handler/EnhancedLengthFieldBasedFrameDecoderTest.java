package org.pillar.codec.binary.handler;

import org.pillar.codec.binary.util.TransportNettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * Created by pillar on 2015/8/11.
 */
public class EnhancedLengthFieldBasedFrameDecoderTest {

    @Test
    public void test_discard_error_byte() throws Exception {
        byte[] bytes = TransportNettyUtil.hexStringToBytes("FAFAF5");
        ByteBuf buf = Unpooled.buffer(1024);
        buf.writeBytes(bytes);
        ByteBuf headBuf = Unpooled.buffer(2);
        headBuf.writeBytes(TransportNettyUtil.hexStringToBytes("FAF5"));
        discard(buf, headBuf, new Object());

    }


    protected Object discard(ByteBuf in, ByteBuf headBuf, Object value) {
        /**
         * 如果value 不为空,如果包头不是0xFaOxF5,
         * 将后面的Buffer清空直到下一个0xFaOxF5,否则会出现长时间解析不了的情况
         */
        in.markReaderIndex();

        ByteBuf cache = Unpooled.buffer(1024, 2048);
        if (in.readableBytes() >= headBuf.readableBytes() + 1) {
            cache.writeBytes(in, 1);
            do {
                cache.writeBytes(in, 1);
                ByteBuf slice = cache.slice(cache.readerIndex(), 2);
                if (slice.equals(headBuf)) {
                    break;
                } else {
                    cache.readByte();
                }
            } while (in.readableBytes() > 0);
        }

        in.resetReaderIndex();
        ByteBuf result = in.readBytes(cache.readerIndex());
        System.out.println(ByteBufUtil.hexDump(result));
        return value;


    }
}