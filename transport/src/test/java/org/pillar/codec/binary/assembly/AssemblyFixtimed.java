//package org.pillar.codec.binary.assembly;
//
//
//import org.pillar.codec.binary.core.Initiator;
//import org.pillar.codec.binary.event.FixTimedChargingInMessage;
//import org.pillar.codec.binary.event.Message;
//import org.pillar.codec.binary.event.MessageHead;
//import org.pillar.codec.binary.transport.ChannelBufferTransport;
//import org.pillar.codec.binary.transport.Protocol;
//import org.pillar.codec.binary.transport.ProtocolFactory;
//import org.pillar.codec.binary.util.TransportNettyUtil;
//import com.google.common.collect.Lists;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.List;
//
//import static org.pillar.codec.binary.event.FixTimedChargingInMessage.FixTimedChargingGun;
//
///**
// * Created by pillar on 2015/9/11.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@ActiveProfiles("test")
//@Transactional
//public class AssemblyFixtimed {
//
//
//    @Autowired
//    private Initiator inititor;
//
//    @Autowired
//    private ProtocolFactory protocolFactory;
//
//    @Test
//    public void test_fix_time() throws Exception {
//        Message<FixTimedChargingInMessage> chargingInMessageMessage = new Message<>();
//        MessageHead messageHead = assemblyHead();
//
//        FixTimedChargingInMessage messageBody = new FixTimedChargingInMessage();
//        messageBody.setFactoryId(1);
//        messageBody.setCdzCode(1024);
//        messageBody.setCurrentDate(new Date(System.currentTimeMillis()));
//        List<FixTimedChargingGun> list = Lists.newArrayList();
//        messageBody.setFixTimedChargingGuns(list);
//
//        chargingInMessageMessage.setHead(messageHead);
//        chargingInMessageMessage.setBody(messageBody);
//
//        FixTimedChargingGun gun = new FixTimedChargingGun();
//        gun.setGunCode(1);
//
//        gun.setAccount("13777777777");
//        gun.setAccountType(2);
//        gun.setOutputEC(400);
//        gun.setOutputVoltage(2048);
//
//        gun.setOutputPower(4096);
//        gun.setCumulativeElectricity(5198);
//        gun.setCumulativeEnergy(5000);
//        gun.setCumulativeTimeSpan(4000);
//        gun.setKwh(400);
//        gun.setStartSoc(255);
//        gun.setCurrentSoc(128);
//        gun.setSoh(80);
//        gun.setBatteryBmsCode("abcdabc");
//        gun.setBatteryVoltage(548);
//        gun.setBatteryEC(50);
//        gun.setSingleMaxVoltage(546);
//        gun.setSingleMinVoltage(528);
//        gun.setMaxTemperaturePosition(80);
//        gun.setMinTemperaturePosition(50);
//
//        gun.setSingleMaxVoltagePosition(2487);
//        gun.setSingleMinVoltagePosition(1024);
//
//        gun.setMaxTempratureValue(100);
//        gun.setMinTempratureValue(46);
//        gun.setEstimateLeftTime(1204);
//        list.add(gun);
//
//
//        ByteBuf byteBuf = Unpooled.buffer(1024, Integer.MAX_VALUE);
//        ChannelBufferTransport channelBufferTransport = new ChannelBufferTransport(new NioSocketChannel(), byteBuf, byteBuf);
//        Protocol protocol = protocolFactory.getOutProtocol(messageBody, FixTimedChargingInMessage.class);
//
//        inititor.to(messageBody, channelBufferTransport, protocol);
//
//        String body = ByteBufUtil.hexDump(byteBuf);
//        System.out.println("body:" + body);
//
//
//        byte[] bytes = TransportNettyUtil.hexStringToBytes("71" + body);
//        String checksum = TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)});
//        System.out.println("checkSum:" + checksum);
//
//        String all = "00" + "71" + body + checksum;
//
//        int length = TransportNettyUtil.hexStringToBytes(all).length;
//
//        System.out.println("lengthField:" + length);
//        System.out.println(TransportNettyUtil.byteToHexString((byte) (length)));
//
////        System.out.println(TransportNettyUtil.byteToHexString((byte) (length & 0xff)) +
////                TransportNettyUtil.byteToHexString((byte) ((length >>> 8) & 0xff)));
//
//    }
//
//
//    private MessageHead assemblyHead() {
//        MessageHead messageHead = new MessageHead();
//        messageHead.setStart("FAF5");
//        messageHead.setLength(0);
//        messageHead.setVersion(0x80);
//        messageHead.setSequence(0x00);
//        messageHead.setCommand(0x71);
//        return messageHead;
//    }
//
//
//    @Test
//    public void test_hex() throws Exception {
//        System.out.println(258 & 0xFF);
//        System.out.println((258 >>> 8) & 0xFF);
//    }
//}
