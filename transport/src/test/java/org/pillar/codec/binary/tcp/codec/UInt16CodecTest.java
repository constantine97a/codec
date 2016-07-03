package org.pillar.codec.binary.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.junit.Before;
import org.junit.Test;
import org.pillar.codec.binary.codec.UInt16Codec;

/**
 * Created by pillar on 2015/10/26.
 */
public class UInt16CodecTest {


    private UInt16Codec codec;

    @Before
    public void setUp() throws Exception {
        codec = new UInt16Codec();


    }

    @Test
    public void testDecoder() throws Exception {
        ByteBuf byteBuf = codec.encode(65535);
        int mask = 0xffff;
        System.out.println(mask);
        System.out.println(mask & 99999);

        System.out.println("===================" + ByteBufUtil.hexDump(byteBuf));
    }

    @Test
    public void testEncoder() throws Exception {

    }
}