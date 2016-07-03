//package org.pillar.codec.binary.assembly;
//
//
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
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by pillar on 2015/9/15.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@ActiveProfiles("test")
//public class AssemblyChargingPolicy {
//	@Autowired
//	private Initiator inititor;
//
//	@Autowired
//	private ProtocolFactory protocolFactory;
//
//	@Test
//	public void test_chargingpolicy_upstream() throws Exception {
//
//
//		Date date = DateUtils.parseDate("2015/11/5 10:22:50", "yyy/MM/dd HH:mm:ss");
//
//		ChargingPolicyUpstream chargingPolicyUpstream = new ChargingPolicyUpstream();
//		chargingPolicyUpstream.setResultCode(1);
//		chargingPolicyUpstream.setFactoryId(1);
//		chargingPolicyUpstream.setCdzCode(1024);
//
//		chargingPolicyUpstream.setTimeStamp(date);
//
//		chargingPolicyUpstream.setGunCode(1);
//		chargingPolicyUpstream.setBootTimestamp(date);
//		chargingPolicyUpstream.setAccount("13777777777");
//
//
//		ByteBuf byteBuf = Unpooled.buffer(1024, Integer.MAX_VALUE);
//		ChannelBufferTransport channelBufferTransport = new ChannelBufferTransport(new NioSocketChannel(), byteBuf, byteBuf);
//		Protocol protocol = protocolFactory.getOutProtocol(chargingPolicyUpstream, ChargingPolicyUpstream.class);
//
//		inititor.to(chargingPolicyUpstream, channelBufferTransport, protocol);
//
//		String body = ByteBufUtil.hexDump(byteBuf);
//		System.out.println("body:" + body);
//
//
//		byte[] bytes = TransportNettyUtil.hexStringToBytes("A1" + body);
//		String checksum = TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)});
//		System.out.println("checkSum:" + checksum);
//
//		String all = "00" + "A1" + body + checksum;
//
//		int length = TransportNettyUtil.hexStringToBytes(all).length;
//		System.out.println("lengthField:" + length);
//
//		System.out.println(TransportNettyUtil.byteToHexString((byte) length));
//
//	}
//}
