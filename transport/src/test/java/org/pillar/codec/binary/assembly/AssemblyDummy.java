//package org.pillar.codec.binary.assembly;
//
//import org.pillar.codec.binary.core.Initiator;
//import org.pillar.codec.binary.event.ChargingFinInMessage;
//import org.pillar.codec.binary.event.DummyInMessage;
//import org.pillar.codec.binary.transport.ChannelBufferTransport;
//import org.pillar.codec.binary.transport.Protocol;
//import org.pillar.codec.binary.transport.ProtocolFactory;
//import org.pillar.codec.binary.util.TransportNettyUtil;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import org.apache.commons.lang3.time.DateUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by pillar on 2015/10/22.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
//public class AssemblyDummy {
//
//
//    @Autowired
//    private Initiator inititor;
//
//    @Autowired
//    private ProtocolFactory protocolFactory;
//
//
//    @Test
//    public void test_assembly_dummy() throws Exception {
//        DummyInMessage dummyInMessage = new DummyInMessage();
//        Date date = new Date();
//        dummyInMessage.setCurrentDate(date);
//        dummyInMessage.setCdzCode(1024);
//        dummyInMessage.setFactoryId(1);
//
//
//
//        ByteBuf byteBuf = Unpooled.buffer(1024, Integer.MAX_VALUE);
//        ChannelBufferTransport channelBufferTransport = new ChannelBufferTransport(new NioSocketChannel(), byteBuf, byteBuf);
//        Protocol protocol = protocolFactory.getOutProtocol(dummyInMessage, DummyInMessage.class);
//
//        inititor.to(dummyInMessage, channelBufferTransport, protocol);
//
//        String body = ByteBufUtil.hexDump(byteBuf);
//        System.out.println("body:" + body);
//
//
//        byte[] bytes = TransportNettyUtil.hexStringToBytes("fe" + body);
//        String checksum = TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)});
//        System.out.println("checkSum:" + checksum);
//
//        String all = "00" + "fe" + body + checksum;
//
//        int length = TransportNettyUtil.hexStringToBytes(all).length;
//        System.out.println("lengthField" + length);
//
//
//        System.out.println(TransportNettyUtil.byteToHexString((byte) length));
//
//    }
//}
