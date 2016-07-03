//package org.pillar.codec.binary.core;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//
//import java.nio.ByteOrder;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.codec.DecoderException;
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
//import org.pillar.codec.binary.codec.UInt16Codec;
//import org.pillar.codec.binary.event.MessageHead;
//import org.pillar.codec.binary.event.PileTotalStatusMessage;
//import org.pillar.codec.binary.model.Black;
//import org.pillar.codec.binary.model.Pile;
//import org.pillar.codec.binary.model.Policy;
//import org.pillar.codec.binary.schema.CompositeField;
//import org.pillar.codec.binary.builder.ProtocolFieldBuilder;
//import org.pillar.codec.binary.service.BlackService;
//import org.pillar.codec.binary.service.PileService;
//import org.pillar.codec.binary.service.PolicyService;
//import org.pillar.codec.binary.service.RegisterPileService;
//import org.pillar.codec.binary.transport.ChannelBufferTransport;
//import org.pillar.codec.binary.transport.Protocol;
//import org.pillar.codec.binary.vo.VoPileGun;
//import org.pillar.codec.binary.vo.VoSignProtocol;
//import org.pillar.codec.binary.util.TransportUtil;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Created by pillar on 2015/8/18.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@Transactional
//@ActiveProfiles("test")
//public class PileTotalStatusTest {
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
//
//    private ChannelBufferTransport transport;
//
//
//    private Protocol protocol;
//
//
//    private Inititor inititor;
//    private CompositeField header;
//
//    private CompositeField body;
//    private ProtocolFieldBuilder builder;
//
//    @Before
//    public void setUp() throws Exception {
//
//    	builder = new ProtocolFieldBuilder();
//
//    	header = builder.createMessageHeadCompositeField();
//
//
//        inititor = new Inititor();
//
//        body = builder.createPileTotalStatusCompositeField();
//
//    }
//
//    @Test
//    public void testPileTotalStatus() throws Exception {
//
//        byte[] bytes = Hex.decodeHex("faf5240080001301110001a0860100a0860100010100000517001700170001010101010101010101ca".toCharArray());
//
//        ByteBuf aa =  Unpooled.copiedBuffer(bytes);
//        ByteBuf bb = Unpooled.buffer(7);
//    	bb.order(ByteOrder.LITTLE_ENDIAN);
//        bb.writeBytes(aa, 0, 7);
//        ByteBuf cc = Unpooled.buffer(aa.readableBytes()-7);
//    	cc.order(ByteOrder.LITTLE_ENDIAN);
//        cc.writeBytes(aa, 7, aa.readableBytes()-7);
//       /*
//        ByteBuf dd = Unpooled.buffer(aa.readableBytes()-30);
//    	dd.order(ByteOrder.LITTLE_ENDIAN);
//        dd.writeBytes(aa, 30, aa.readableBytes()-30);
//        */
//        protocol = new Protocol( header);
//
//        transport = new ChannelBufferTransport(null, bb, null);
//
//        MessageHead h = inititor.from(transport, protocol);
//
//        System.out.println("head:" + h.toString());
//
//        protocol = new Protocol(body);
//
//        transport = new ChannelBufferTransport(null, cc, null);
//
//        PileTotalStatusMessage h1 = inititor.from(transport, protocol);
//
//        Pile pile = new Pile();
//
//
//
//        System.out.println("body:" + h1.toString());
//
//        List<VoPileGun> vpg = new ArrayList<VoPileGun>();
//
//        /*
//        if(h1.getGuncount()!=null && h1.getGuncount()!=0) {
//        	for(int i =0;i<h1.getGuncount();i++) {
//        		VoPileGun vo = new VoPileGun();
//        		//dd.resetReaderIndex();
//        		dd.readableBytes();
//        		vo.setGuntype(dd.readByte()&0xff);
//        		vo.setGuncode(dd.readByte()&0xff);
//        		vpg.add(vo);
//        	}
//        }*/
//
//    }
//
///*0000000000000000000000000000000000000000
// *3c000c006400000600e90c0012005812001800a8
// *06000c006400000600e90c0012005812001800a8
//    @Test
//    public void test_write_header() {
//        MessageHead h = new MessageHead();
//        h.setStart("faf5");
//        h.setLength(4);
//        h.setCommand(81);
//        h.setSequence(10);
//        h.setVersion(80);
//
//        transport = new ChannelBufferTransport(null, null, Unpooled.buffer(1024));
//        transport.getBuffer();
//        protocol = new Protocol(MessageHead.class, header);
//
//        inititor.to(h, transport, protocol);
//
//        System.out.println(ByteBufUtil.hexDump(((ChannelBufferTransport)transport).getOut()));
//
//    }*/
//
//
//    protected ByteBuf writeByteBufPileRegister(VoSignProtocol vsp,Policy policy,List<Black> lb) throws DecoderException {
//    	ByteBuf byteBuf = Unpooled.buffer(1024);
//    	byteBuf.writeBytes(Hex.decodeHex("faf5".toCharArray()));
//		UInt16Codec u16 = new UInt16Codec();
//		byteBuf.writeBytes(u16.encode(0));
//		byteBuf.writeByte(0x80);
//		byteBuf.writeByte(0);
//		byteBuf.writeByte(0x21);
//		//充电桩编码
//		byteBuf.writeByte(vsp.getFactoryid());
//		byteBuf.writeBytes(u16.encode(vsp.getCdzcode()));
//		//标准时钟间
//		byteBuf.writeBytes(TransportUtil.dateToByte(new Date()));
//		//计量类型
//		byteBuf.writeByte(1);
//
//		if(policy != null) {
//			//尖时段 开始
//			byteBuf.writeByte(policy.getTiptimesh());
//			byteBuf.writeByte(policy.getTiptimesm());
//			//尖时段结束
//			byteBuf.writeByte(policy.getTiptimeeh());
//			byteBuf.writeByte(policy.getTiptimeem());
//			//尖时段电价
//			byteBuf.writeByte(policy.getTipprice());
//
//			//峰时段 开始
//			byteBuf.writeByte(policy.getPeaktimesh());
//			byteBuf.writeByte(policy.getPeaktimesm());
//			//峰时段结束
//			byteBuf.writeByte(policy.getPeaktimeeh());
//			byteBuf.writeByte(policy.getPeaktimeem());
//			//峰时段电价
//			byteBuf.writeByte(policy.getPeakprice());
//
//
//			//平时段 开始
//			byteBuf.writeByte(policy.getUsuallytimesh());
//			byteBuf.writeByte(policy.getUsuallytimesm());
//			//平时段结束
//			byteBuf.writeByte(policy.getUsuallytimeeh());
//			byteBuf.writeByte(policy.getUsuallytimeem());
//			//平时段电价
//			byteBuf.writeByte(policy.getUsuallyprice());
//
//			//谷时段 开始
//			byteBuf.writeByte(policy.getValleytimesh());
//			byteBuf.writeByte(policy.getValleytimesm());
//			//谷时段结束
//			byteBuf.writeByte(policy.getValleytimeeh());
//			byteBuf.writeByte(policy.getValleytimeem());
//			//谷时段电价
//			byteBuf.writeByte(policy.getValleyprice());
//		}else {
//			//尖时段 开始
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//尖时段结束
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//尖时段电价
//			byteBuf.writeByte(0);
//
//			//峰时段 开始
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//峰时段结束
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//峰时段电价
//			byteBuf.writeByte(0);
//
//
//			//平时段 开始
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//平时段结束
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//平时段电价
//			byteBuf.writeByte(0);
//
//			//谷时段 开始
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//谷时段结束
//			byteBuf.writeByte(0);
//			byteBuf.writeByte(0);
//			//谷时段电价
//			byteBuf.writeByte(0);
//		}
//		//黑名单
//		Date blackDate = new Date();
//		if(lb == null || lb.size()==0) {
//			byteBuf.writeByte(0);
//			byteBuf.writeBytes(TransportUtil.dateToByte(blackDate));
//		} else {
//			byteBuf.writeByte(lb.size());
//			byteBuf.writeBytes(TransportUtil.dateToByte(lb.get(lb.size()).getCreateTime()));
//			for(Black bk : lb) {
//				byteBuf.writeBytes(Hex.decodeHex(bk.getIccode().toCharArray()));
//				byteBuf.writeByte(bk.getStatus());
//			}
//		}
//
//
//		return byteBuf;
//	}
//
//
//
//   /* @Test
//    public void test_write_body() throws Exception {
//        body = new CompositeField("body", TestMessageBody.class);
//        body.addField(new UInt8PField("value"));
//        body.addField(new StringPField("stringValue", 2));
//
//        CompositeField list = new CompositeField("values", List.class);
//        CompositeField item = new CompositeField("", Value.class);
//        item.addField(new UInt8PField("v"));
//        item.addField(new StringPField("b", 2));
//
//        list.addField(item);
//        list.addField(item);
//        body.addField(list);
//
//
//        CompositeField list2 = new CompositeField("values2", List.class);
//        list2.addField(item);
//        list2.addField(item);
//
//        body.addField(list2);
//
//        transport = new ChannelBufferTransport(null, null, Unpooled.buffer(1024));
//
//        protocol = new Protocol(TestMessageBody.class, body);
//
//
//        TestMessageBody messageBody = new TestMessageBody();
//        messageBody.setValue(80);
//        messageBody.setStringValue("st");
//
//
//        final Value value = new Value();
//        value.setB("bb");
//        value.setV(10);
//
//        List<Value> values=new ArrayList<Value>(){{
//            this.add(value);
//            this.add(value);
//        }};
//
//        messageBody.setValues(values);
//        messageBody.setValues2(values);
//
//        inititor.to(messageBody, transport, protocol);
//        System.out.println(transport);
//
//
//    }*/
//}