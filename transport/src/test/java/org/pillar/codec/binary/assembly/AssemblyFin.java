//package org.pillar.codec.binary.assembly;
//
//import org.pillar.codec.binary.core.Initiator;
//import org.pillar.codec.binary.event.ChargingFinInMessage;
//import org.pillar.codec.binary.event.ChargingFinInMessage.ChargingFinInItem;
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
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by pillar on 2015/9/11.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@ActiveProfiles("test")
//@Transactional
//public class AssemblyFin {
//
//
//    @Autowired
//    private Initiator inititor;
//
//    @Autowired
//    private ProtocolFactory protocolFactory;
//
//    /**
//     * 手机用户 正常结算
//     *
//     * @throws Exception
//     */
//    @Test
//    public void test_fin() throws Exception {
//
//        ChargingFinInMessage chargingFinInMessage = new ChargingFinInMessage();
//        Date date = new Date();
//        chargingFinInMessage.setCurrentDate(date);
//        chargingFinInMessage.setCdzCode(1024);
//        chargingFinInMessage.setFactoryId(1);
//
//        chargingFinInMessage.setBillCount(1);
//
//        List<ChargingFinInItem> chargingFinInItems = new ArrayList<>();
//        ChargingFinInItem chargingFinInItem = new ChargingFinInItem();
//        chargingFinInItem.setGunCode(1);
//        chargingFinInItem.setBillSerialNum(new ChargingFinInMessage.BillSerialNumber(new Date(date.getTime() + 1024), 1, 1024, 1));
//        chargingFinInItem.setAccountType(2);
//        chargingFinInItem.setAccount("15195751901");
//        chargingFinInItem.setAccountStatus(1);
//        chargingFinInItem.setCurTotalPower(5189);
//        chargingFinInItem.setCurTotalEnergy(1024);
//        chargingFinInItem.setCurTotalDuration(1555);
//        chargingFinInItem.setStartSoc(51);
//        chargingFinInItem.setEndSoc(4978);
//
//        chargingFinInItem.setCurConsumeAmount(1232345L);
//        chargingFinInItem.setRemainingAmount(987654L);
//        chargingFinInItem.setChargingPolicy(1);
//
//        chargingFinInItem.setChargingBeginTime(DateUtils.addDays(date, -1));
//        chargingFinInItem.setChargingEndTime(date);
//        chargingFinInItem.setChargingResultCode(1);
//
//
//        chargingFinInItems.add(chargingFinInItem);
//        chargingFinInMessage.setChargingFinInItems(chargingFinInItems);
//
//        ByteBuf byteBuf = Unpooled.buffer(1024, Integer.MAX_VALUE);
//        ChannelBufferTransport channelBufferTransport = new ChannelBufferTransport(new NioSocketChannel(), byteBuf, byteBuf);
//        Protocol protocol = protocolFactory.getOutProtocol(chargingFinInMessage, ChargingFinInMessage.class);
//
//        inititor.to(chargingFinInMessage, channelBufferTransport, protocol);
//
//        String body = ByteBufUtil.hexDump(byteBuf);
//        System.out.println("body:" + body);
//
//
//        byte[] bytes = TransportNettyUtil.hexStringToBytes("73" + body);
//        String checksum = TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)});
//        System.out.println("checkSum:" + checksum);
//
//        String all = "00" + "73" + body + checksum;
//
//        int length = TransportNettyUtil.hexStringToBytes(all).length;
//        System.out.println("lengthField" + length);
//
//
//        System.out.println(TransportNettyUtil.byteToHexString((byte) length));
//
//
//    }
//
//
//    @Test
//    public void test_ufin() throws Exception {
//        ChargingFinInMessage chargingFinInMessage = new ChargingFinInMessage();
//        Date date = new Date();
//        chargingFinInMessage.setCurrentDate(date);
//
//        chargingFinInMessage.setCdzCode(1024);
//
//        chargingFinInMessage.setFactoryId(1);
//
//        chargingFinInMessage.setBillCount(1);
//
//        List<ChargingFinInItem> chargingFinInItems = new ArrayList<>();
//        ChargingFinInItem chargingFinInItem = new ChargingFinInItem();
//        chargingFinInItem.setGunCode(0x01);
//        chargingFinInItem.setBillSerialNum(new ChargingFinInMessage.BillSerialNumber(date, 1, 1024, 1));
//        chargingFinInItem.setAccountType(1);
//        chargingFinInItem.setAccount("123456789");
//        chargingFinInItem.setAccountStatus(1);
//        chargingFinInItem.setCurTotalPower(5189);
//        chargingFinInItem.setCurTotalEnergy(1024);
//        chargingFinInItem.setCurTotalDuration(1555);
//        chargingFinInItem.setStartSoc(51);
//        chargingFinInItem.setEndSoc(255);
//
//        chargingFinInItem.setCurConsumeAmount(1232345L);
//        chargingFinInItem.setRemainingAmount(987654L);
//        chargingFinInItem.setChargingPolicy(1);
//
//        chargingFinInItem.setChargingBeginTime(DateUtils.addDays(date, -1));
//        chargingFinInItem.setChargingEndTime(date);
//        chargingFinInItem.setChargingResultCode(1);
//
//
//        chargingFinInItems.add(chargingFinInItem);
//        chargingFinInMessage.setChargingFinInItems(chargingFinInItems);
//
//        ByteBuf byteBuf = Unpooled.buffer(1024, Integer.MAX_VALUE);
//        ChannelBufferTransport channelBufferTransport = new ChannelBufferTransport(new NioSocketChannel(), byteBuf, byteBuf);
//        Protocol protocol = protocolFactory.getOutProtocol(chargingFinInMessage, ChargingFinInMessage.class);
//
//        inititor.to(chargingFinInMessage, channelBufferTransport, protocol);
//
//        String body = ByteBufUtil.hexDump(byteBuf);
//        System.out.println("body" + body);
//
//
//        byte[] bytes = TransportNettyUtil.hexStringToBytes("73" + body);
//        String checksum = TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)});
//        System.out.println("checkSum:" + checksum);
//
//        String all = "00" + "73" + body + checksum;
//
//        int length = TransportNettyUtil.hexStringToBytes(all).length;
//        System.out.println("lengthField" + length);
//
//
//        System.out.println(TransportNettyUtil.byteToHexString((byte) length));
//    }
//}
