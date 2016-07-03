package org.pillar.codec.binary.codec;

import org.pillar.codec.binary.util.TransportNettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import org.junit.Before;
import org.junit.Test;
import org.pillar.codec.binary.codec.ByteDateCodec;

import java.util.Date;

/**
 * Created by pillar on 2015/8/22.
 */
public class ByteDateCodecTest {

    private ByteDateCodec byteDateCodec;

    @Before
    public void setUp() throws Exception {
        byteDateCodec = new ByteDateCodec();

    }

    @Test
    public void testEncode() throws Exception {
        Date date = new Date(1440999710750L);
        ByteBuf byteBuf = byteDateCodec.encode(date);
        try {
            System.out.println(ByteBufUtil.hexDump(byteBuf));
        } finally {
            ReferenceCountUtil.release(byteBuf);
        }

    }

    @Test
    public void test_caculation_checksum() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        byte[] bytes = TransportNettyUtil.hexStringToBytes("93010004df07081f0a0c07ff6162636461626364000000000000000080");
        System.out.println("checkSum:" + TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)}));


        String all = "faf50000800093010004df07081f0a0c07ff616263646162636400000000000000008055";

        int length = TransportNettyUtil.hexStringToBytes(all).length - 5;
        System.out.println(length);


        System.out.println(TransportNettyUtil.byteToHexString((byte) length));


    }

    @Test
    public void test_getTime() throws Exception {

        System.out.println(new Date().getTime());
        System.out.println(TransportNettyUtil.bytesToHexString(TransportNettyUtil.dateToByte(new Date(1440667225251L))));

    }
}