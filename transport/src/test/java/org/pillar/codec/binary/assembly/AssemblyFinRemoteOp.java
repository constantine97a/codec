//package org.pillar.codec.binary.assembly;
//
//import org.pillar.codec.binary.core.Initiator;
//import org.pillar.codec.binary.event.RemoteOpUpstream;
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
//import java.util.Date;
//
///**
// * Created by pillar on 2015/9/17.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@ActiveProfiles("test")
//@Transactional
//
//public class AssemblyFinRemoteOp {
//
//	@Autowired
//	private Initiator inititor;
//
//	@Autowired
//	private ProtocolFactory protocolFactory;
//
//	@Test
//	public void test_assembly() throws Exception {
//
//
//		Date date = DateUtils.parseDate("2015/11/10 15:43:04", "yyy/MM/dd HH:mm:ss");
//
//
//		RemoteOpUpstream remoteOpUpstream = new RemoteOpUpstream();
//		remoteOpUpstream.setResultCode(1);
//		remoteOpUpstream.setFactoryId(1);
//		remoteOpUpstream.setCdzCode(1024);
//		remoteOpUpstream.setCurCdzStatus(1);
//		remoteOpUpstream.setTimestamp(date);
//
//		ByteBuf byteBuf = Unpooled.buffer(1024, Integer.MAX_VALUE);
//		ChannelBufferTransport channelBufferTransport = new ChannelBufferTransport(new NioSocketChannel(), byteBuf, byteBuf);
//		Protocol protocol = protocolFactory.getOutProtocol(remoteOpUpstream, RemoteOpUpstream.class);
//
//		inititor.to(remoteOpUpstream, channelBufferTransport, protocol);
//		String body = ByteBufUtil.hexDump(byteBuf);
//		System.out.println("body:" + body);
//
//
//		byte[] bytes = TransportNettyUtil.hexStringToBytes("e1" + body);
//		String checksum = TransportNettyUtil.bytesToHexString(new byte[]{TransportNettyUtil.checksum(bytes)});
//		System.out.println("checkSum:" + checksum);
//
//		String all = "00" + "e1" + body + checksum;
//
//		int length = TransportNettyUtil.hexStringToBytes(all).length;
//		System.out.println("lengthField:" + length);
//
//		System.out.println("lengthCode:" + TransportNettyUtil.byteToHexString((byte) length));
//
//
//	}
//}
