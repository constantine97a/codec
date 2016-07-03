package org.pillar.codec.binary.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.junit.Test;
import org.pillar.codec.binary.codec.UInt16Codec;
import org.pillar.codec.binary.codec.UInt32Codec;
import org.pillar.codec.binary.codec.UInt8Codec;

import static org.junit.Assert.assertEquals;

/**
 * Created by pillar on 2015/10/26.
 */
public class UIntCodecTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_encode() throws Exception {
        UInt8Codec codec = new UInt8Codec();
        ByteBuf byteBuf = codec.encode(1024);
    }

    @Test
    public void test_encode_() throws Exception {
        UInt8Codec codec = new UInt8Codec();
        ByteBuf byteBuf = codec.encode(0xff);
        System.out.println(ByteBufUtil.hexDump(byteBuf));
        assertEquals(Integer.valueOf(0xff), codec.decode(byteBuf));
    }

    @Test
    public void test_encode_uint16() throws Exception {
        UInt16Codec codec = new UInt16Codec();
        ByteBuf byteBuf = codec.encode(
                0xffff);
        System.out.println(ByteBufUtil.hexDump(byteBuf));
        assertEquals(Integer.valueOf(0xffff), codec.decode(byteBuf));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_encode_ex() throws Exception {
        try {
            UInt16Codec codec = new UInt16Codec();
            ByteBuf byteBuf = codec.encode(
                    0x010000);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Test
    public void test_encode_uint32() throws Exception {
        UInt32Codec codec = new UInt32Codec();
        ByteBuf byteBuf = codec.encode(0xffffffffL);
        System.out.println(ByteBufUtil.hexDump(byteBuf));
        assertEquals(Long.valueOf(0xffffffffL), codec.decode(byteBuf));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_encode_uint32_ex() throws Exception {
        try {
            UInt32Codec codec = new UInt32Codec();
            ByteBuf byteBuf = codec.encode(0x0100000000L);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}