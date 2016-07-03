//package org.pillar.codec.binary.assembly;
//
//import org.pillar.codec.binary.core.Initiator;
//import org.pillar.codec.binary.event.StopChargingUpstream;
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
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//
//import java.util.Date;
//
///**
// * Created by pillar on 2015/9/16.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@ActiveProfiles("test")
//public class AssemblyStopCharging {
//
//    @Autowired
//    private Initiator inititor;
//
//    @Autowired
//    private ProtocolFactory protocolFactory;
//
//    @Test
//    public void test_assembly_stop_charging_response() throws Exception {
////		Date date = new Date(1442400922000L);
//
//        Date date = DateUtils.parseDate("2015/11/5 10:29:48", "yyy/MM/dd HH:mm:ss");
//        StopChargingUpstream stopChargingUpstream = new StopChargingUpstream();
//        stopChargingUpstream.setResultCode(1);
//        stopChargingUpstream.setFactoryId(1);
//        stopChargingUpstream.setCdzCode(1024);
//        stopChargingUpstream.setCurrentDate(date);
//        stopChargingUpstream.setGunCode(1);
//        stopChargingUpstream.setAccount("13777777777");
//
//
//        ByteBuf byteBuf = Unpooled.buffer(1024, Integer.MAX_VALUE);
//        ChannelBufferTransport channelBufferTransport = new ChannelBufferTransport(new NioSocketChannel(), byteBuf, byteBuf);
//        Protocol protocol = protocolFactory.getOutProtocol(stopChargingUpstream, StopChargingUpstream.class);
//
//        inititor.to(stopChargingUpstream, channelBufferTransport, protocol);
//
//        String body = ByteBufUtil.hexDump(byteBuf);
//        System.out.println("body" + body);
//
//
//        byte[] bytes = TransportNettyUtil.hexStringToBytes("A3" + body);
//        String checksum = TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)});
//        System.out.println("checkSum:" + checksum);
//
//        String all = "00" + "A3" + body + checksum;
//
//        int length = TransportNettyUtil.hexStringToBytes(all).length;
//        System.out.println("lengthField:" + length);
//
//        System.out.println(TransportNettyUtil.byteToHexString((byte) length));
//
//    }
//}
