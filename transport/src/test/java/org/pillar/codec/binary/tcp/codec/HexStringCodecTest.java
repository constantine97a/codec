package org.pillar.codec.binary.codec;

import com.google.common.base.Function;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.junit.Before;
import org.junit.Test;
import org.pillar.codec.binary.codec.HexStringCodec;

import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pillar on 2015/8/14.
 */
public class HexStringCodecTest {

    /**
     * 将Hex string转换
     */
    private HexStringCodec codec;

    @Before
    public void setUp() throws Exception {
        codec = new HexStringCodec(Charset.forName("US-ASCII"));
    }

    @Test
    public void testDecoder() throws Exception {
        assertNotNull(codec.decoder());
    }

    @Test
    public void testDecoder_with_consume() throws Exception {
        Function<ByteBuf, String> decoder = codec.decoder();
        assertNotNull(decoder);

    }

    @Test
    public void testEncoder() throws Exception {
        assertNotNull(codec.encoder());
    }

    @Test
    public void testApply() throws Exception {
        ByteBuf bytes = codec.encode("faf5");
        byte actual = bytes.readByte();
        assertEquals(-6, actual);
        actual = bytes.readByte();
        assertEquals(-11, actual);
    }

    @Test
    public void testEncode() throws Exception {
        ByteBuf byteBuf = codec.encode("FAF504080012");
        assertEquals("FAF504080012".toLowerCase(), ByteBufUtil.hexDump(byteBuf));

    }

    @Test
    public void testDecode() throws Exception {
        ByteBuf byteBuf = codec.encode("FAF504080012");
        Function<ByteBuf, String> decoder = codec.decoder();
        String result = decoder.apply(byteBuf);
        assertEquals("FAF504080012".toLowerCase(), result);
    }
}