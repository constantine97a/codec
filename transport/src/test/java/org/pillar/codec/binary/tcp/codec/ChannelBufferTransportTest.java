package org.pillar.codec.binary.codec;

import org.pillar.codec.binary.transport.ChannelBufferTransport;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

/**
 * Created by pillar on 2015/8/13.
 */
public class ChannelBufferTransportTest {


    private ChannelBufferTransport channelBufferTransport;

    @Before
    public void setUp() throws Exception {

        ByteBuf in = Unpooled.buffer(1024);
        in.order(ByteOrder.LITTLE_ENDIAN);
        ByteBuf out = Unpooled.buffer(1024);
        out.order(ByteOrder.LITTLE_ENDIAN);

        channelBufferTransport = new ChannelBufferTransport(null, in, out);


    }

    @Test
    public void test_set_header() throws Exception {
        String hexString = "faf5";
        byte[] bytes = Hex.decodeHex(hexString.toCharArray());
        channelBufferTransport.write(bytes, 0, 2);
        ByteBuf byteBuf = channelBufferTransport.getOut();
        assertEquals(hexString, ByteBufUtil.hexDump(byteBuf));
    }

    @Test
    public void test_write_string() throws Exception {
        channelBufferTransport.writeString("abc", Charset.forName("US-ASCII"));
        byte[] bytes = new byte[3];
        channelBufferTransport.getOut().readBytes(bytes);
        assertEquals("abc", new String(bytes, Charset.forName("US-ASCII")));
    }

    @Test
    public void test_write_uint16() throws Exception {
        channelBufferTransport.writeUInt16(254);
        channelBufferTransport.writeUInt16(253);
        channelBufferTransport.writeUInt16(256);

        int s = channelBufferTransport.getOut().readUnsignedShort();
        int s2 = channelBufferTransport.getOut().readUnsignedShort();
        int s3 = channelBufferTransport.getOut().readUnsignedShort();
        assertEquals(254, s);
        assertEquals(253, s2);
        assertEquals(256, s3);
    }

    @Test
    public void test_write_uint32() throws Exception {
        channelBufferTransport.writeUInt32(2048);
        channelBufferTransport.writeUInt32(1024);
        channelBufferTransport.writeUInt32(9868);

        int s = (int) channelBufferTransport.getOut().readUnsignedInt();
        int s2 = (int) channelBufferTransport.getOut().readUnsignedInt();
        int s3 = (int) channelBufferTransport.getOut().readUnsignedInt();
        assertEquals(2048, s);
        assertEquals(1024, s2);
        assertEquals(9868, s3);
    }


}