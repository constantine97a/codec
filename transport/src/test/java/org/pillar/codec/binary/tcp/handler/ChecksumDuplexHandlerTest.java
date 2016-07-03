//package org.pillar.codec.binary.handler;
//
//import org.pillar.codec.binary.config.NettyCheckFrameConfig;
//
//import org.pillar.codec.binary.util.TransportNettyUtil;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by pillar on 2015/8/11.
// */
//public class ChecksumDuplexHandlerTest {
//    private ChecksumDuplexHandler checksumDuplexHandler;
//
//
//    @Before
//    public void setUp() throws Exception {
//        checksumDuplexHandler = new ChecksumDuplexHandler(new NettyCheckFrameConfig(6, 1));
//
//    }
//
//    @Test
//    public void test_setChecksum() throws Exception {
//        ByteBuf byteBuf = Unpooled.buffer(100);
//        byteBuf.writeBytes(TransportNettyUtil.hexStringToBytes("faf50400800001010230"));
//        checksumDuplexHandler.setChecksum(byteBuf);
//        System.out.println(ByteBufUtil.hexDump(byteBuf));
//    }
//}