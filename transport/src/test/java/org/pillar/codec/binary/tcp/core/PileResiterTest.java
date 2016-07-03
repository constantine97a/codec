//package org.pillar.codec.binary.core;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//
//import java.nio.ByteOrder;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.codec.binary.Hex;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//
//import org.pillar.codec.binary.event.MessageHead;
//import org.pillar.codec.binary.event.PileRegisterMessage;
//import org.pillar.codec.binary.event.PrResponseMessage;
//import org.pillar.codec.binary.model.Black;
//import org.pillar.codec.binary.model.BlackList;
//import org.pillar.codec.binary.model.Policy;
//import org.pillar.codec.binary.model.RegisterPile;
//import org.pillar.codec.binary.schema.CompositeField;
//import org.pillar.codec.binary.builder.ProtocolFieldBuilder;
//import org.pillar.codec.binary.schema.StringPField;
//import org.pillar.codec.binary.schema.UInt8PField;
//import org.pillar.codec.binary.service.BlackService;
//import org.pillar.codec.binary.service.PileService;
//import org.pillar.codec.binary.service.PolicyService;
//import org.pillar.codec.binary.service.RegisterPileService;
//import org.pillar.codec.binary.transport.ChannelBufferTransport;
//import org.pillar.codec.binary.transport.Protocol;
//import com.pillar.pile.tools.mapper.BeanMapper;
//import org.pillar.codec.binary.util.DateUtil;
//
///**
// * Created by pillar on 2015/8/18.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/applicationContext-test.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@ActiveProfiles("test")
//public class PileResiterTest {
//
//	@Autowired
//	private PolicyService pservice;
//
//	@Autowired
//	private RegisterPileService registerPileService;
//
//	@Autowired
//	private BlackService bservice;
//
//	@Autowired
//	private PileService pileService;
//
//	private ChannelBufferTransport transport;
//
//	private Protocol protocol;
//
//	private Inititor inititor;
//	private CompositeField header;
//
//	private CompositeField body;
//
//	private ProtocolFieldBuilder builder;
//
//	@Before
//	public void setUp() throws Exception {
//
//		builder = new ProtocolFieldBuilder();
//
//		header = builder.createMessageHeadCompositeField();
//
//		inititor = new Inititor();
//
//		body = builder.createPileRegisterCompositeField();
//
//	}
//
//	@Test
//	public void testrp() throws Exception {
//
//		byte[] bytes = Hex
//				.decodeHex("faf52e00800011011100000000006f0000006f0000006f000000df070813101918ffdf070813101918ffdf070813101918ff33"
//						.toCharArray());
//
//		ByteBuf aa = Unpooled.copiedBuffer(bytes);
//		ByteBuf bb = Unpooled.buffer(7);
//		bb.order(ByteOrder.LITTLE_ENDIAN);
//		bb.writeBytes(aa, 0, 7);
//		ByteBuf cc = Unpooled.buffer(aa.readableBytes() - 7);
//		cc.order(ByteOrder.LITTLE_ENDIAN);
//		cc.writeBytes(aa, 7, aa.readableBytes() - 7);
//		protocol = new Protocol(header);
//
//		transport = new ChannelBufferTransport(null, bb, null);
//
//		MessageHead messageHead = inititor.from(transport, protocol);
//
//		protocol = new Protocol(body);
//
//		transport = new ChannelBufferTransport(null, cc, null);
//
//		PileRegisterMessage messageBody = inititor.from(transport, protocol);
//
//		RegisterPile rp = new RegisterPile();
//		BeanMapper.copy(messageBody, rp);
//		rp.setActcdzcode(messageBody.getFactoryid() + "-"
//				+ messageBody.getCdzcode());
//		registerPileService.insertRegisterPile(rp);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("createDate", DateUtil.convertoDate(new Date()));
//		map.put("districtId", 37);
//		Policy policy = pservice.getMinPolicyToday(map);
//		if (policy == null) {
//			policy = new Policy();
//		}
//		List<Black> lb = bservice.getBlackByDate(map);
//
//		MessageHead h = new MessageHead();
//		h.setStart("faf5");
//		h.setLength(0);
//		h.setCommand(0x21);
//		h.setSequence(0);
//		h.setVersion(0x80);
//		transport = new ChannelBufferTransport(null, null,
//				Unpooled.buffer(1024));
//		transport.getBuffer();
//		protocol = new Protocol(header);
//		inititor.to(h, transport, protocol);
//
//		ByteBuf headByte = transport.getOut();
//
//		System.out.println(ByteBufUtil.hexDump(headByte));
//
//		PrResponseMessage resMessage = new PrResponseMessage();
//
//		resMessage.setFactoryid(messageBody.getFactoryid());
//		resMessage.setCdzcode(messageBody.getCdzcode());
//		resMessage.setInterfaceTime(new Date());
//		resMessage.setUnit(1);
//		BeanMapper.copy(policy, resMessage);
//		resMessage.setBlackCount(lb.size());
//		CompositeField prcf = ProtocolFieldBuilder.getInstance()
//				.createPrResponseCompositeField();
//		if (lb == null || lb.size() == 0) {
//			resMessage.setBlackDate(new Date());
//		} else {
//			resMessage.setBlackDate(lb.get(lb.size() - 1).getCreateTime());
//			List<BlackList> blacklist = BeanMapper.mapList(lb, BlackList.class);
//			;
//			resMessage.setBlacklist(blacklist);
//
//			CompositeField list = new CompositeField("blacklist", List.class);
//			CompositeField item = new CompositeField("", BlackList.class);
//			item.addField(new StringPField("iccode", 16));
//			item.addField(new UInt8PField("status"));
//
//			for(int i=0;i<lb.size();i++) {
//				list.addField(item);
//			}
//
//			prcf.addField(list);
//
//		}
//
//		 transport = new ChannelBufferTransport(null, null, Unpooled.buffer(1024));
//
//	     protocol = new Protocol(prcf);
//
//	     inititor.to(resMessage, transport, protocol);
//
//	     System.out.println(ByteBufUtil.hexDump(transport.getOut()));
//
//	}
//
//}