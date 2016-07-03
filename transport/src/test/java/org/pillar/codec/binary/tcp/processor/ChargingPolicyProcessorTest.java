//package org.pillar.codec.binary.processor;
//
//import org.pillar.codec.binary.event.ChargingPolicyDownstream;
//import org.pillar.codec.binary.event.Message;
//import org.pillar.codec.binary.event.MessageHead;
//import org.pillar.codec.binary.transport.ChannelBufferTransport;
//import org.pillar.codec.binary.transport.Transport;
//import org.pillar.codec.binary.util.TransportNettyUtil;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import org.apache.commons.codec.DecoderException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//
///**
// * Created by pillar on 2015/8/27.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@ActiveProfiles("test")
//@Transactional
//public class ChargingPolicyProcessorTest {
//	@Autowired
//	ChargingPolicyProcessor chargingPolicyProcessor;
//
//	@Autowired
//	HeadProcessor headProcessor;
//
//	@Test
//	public void testDispatch() throws Exception {
//
////        ChargingPolicyDownstream value = new ChargingPolicyDownstream();
////        value.setFactoryId(1);
////        value.setCdzCode(1024);
////        value.setCurrentTimestamp(new Date(0));
////        value.setAccount("abcdabcdabcd");
////        value.setGunCode(8);
////        value.setChargingPolicy(1);
////        value.setChargingPolicyArg(16);
////
////        Message<ChargingPolicyDownstream> message = new Message<>();
////        message.setHead(new MessageHead("faf5", 20, 0x80, 00, 0x91));
////        message.setBody(value);
////
////       chargingPolicyProcessor.handle(message);
//
////        chargingPolicyProcessor.processin
//
//
//	}
//
//	@Test
//	public void test_process_in() throws Exception {
//		Message message = new Message();
//		byte[] bytes = TransportNettyUtil.hexStringToBytes("010040df07081b111419ff0101df0708160f052dff6162636461626364616263646162636496");
//		Transport transport = new ChannelBufferTransport(new NioSocketChannel(), Unpooled.wrappedBuffer(bytes), 1024);
//		RequestContext requestContext = new RequestContextImpl(transport, transport);
//		DefaultProcessorPipeline processorPipeline = new DefaultProcessorPipeline();
//		chargingPolicyProcessor.processIn(message, requestContext, new DefaultProcessorContext(processorPipeline, chargingPolicyProcessor, "chargingPolicyProcessor"));
//
//	}
//
//	@Test
//	public void test_process_in_() throws DecoderException {
//		Message message = new Message();
//		byte[] bytes = TransportNettyUtil.hexStringToBytes("faf528008000A1010040df07081b111419ff0101df0708160f052dff6162636461626364616263646162636496");
//		Transport transport = new ChannelBufferTransport(new NioSocketChannel(), Unpooled.wrappedBuffer(bytes), 1024);
//		RequestContext requestContext = new RequestContextImpl(transport, transport);
//		DefaultProcessorPipeline processorPipeline = new DefaultProcessorPipeline();
//		processorPipeline.addLast(headProcessor);
//		processorPipeline.addLast(chargingPolicyProcessor);
//
//		processorPipeline.processIn(message, requestContext);
//
//		System.out.println(message);
//	}
//
//	@Test
//	public void test_process_out() throws Exception {
//		Message message = new Message();
//
//		ChargingPolicyDownstream downstream = new ChargingPolicyDownstream(0x1, 1024,new Date(0), "abcdabcdabcd", 0x01, 1, 1);
//		message.setHead(new MessageHead("faf5", 0, 0x80, 0b0, 0x91));
//		message.setBody(downstream);
//
//		Transport transport = new ChannelBufferTransport(new NioSocketChannel(), Unpooled.buffer(1024, Integer.MAX_VALUE), 1024);
//		RequestContext requestContext = new RequestContextImpl(transport, transport);
//		DefaultProcessorPipeline processorPipeline = new DefaultProcessorPipeline();
//		processorPipeline.addLast(headProcessor);
//		processorPipeline.addLast(chargingPolicyProcessor);
//		processorPipeline.processOut(message, requestContext);
//		System.out.println(message);
//	}
//
//}