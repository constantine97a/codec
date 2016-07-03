//package org.pillar.codec.binary.processor;
//
//import org.pillar.codec.binary.event.Message;
//import org.pillar.codec.binary.event.MessageHead;
//import org.pillar.codec.binary.event.StopChargingDownstream;
//import org.pillar.codec.binary.mgt.SocketChannelKey;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import reactor.bus.Event;
//import reactor.bus.EventBus;
//
//import java.util.Date;
//
///**
// * Created by pillar on 2015/8/31.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager")
//@Transactional
//public class StopChargingProcessorTest {
//
//	@Autowired
//	private StopChargingProcessor stopChargingProcessor;
//
//	@Autowired
//	private EventBus eventBus;
//
//
//	@Test
//	public void testDispatch() throws Exception {
//		Message<StopChargingDownstream> message = new Message<>();
//
//		message.setHead(new MessageHead("faf5", 0, 0x80, 0x00, 0x93));
//		StopChargingDownstream body = new StopChargingDownstream();
//		body.setGunCode(0x80);
//		body.setFactoryId(0x01);
//		body.setCdzCode(0x400);
//		body.setAccount("abcdabcd");
//		body.setCurrentDate(new Date());
//		message.setBody(body);
//
//		stopChargingProcessor.getChannelManager().put(SocketChannelKey.valueOf(message.getBody().getChannelKey()), new NioSocketChannel());
//
//
//		eventBus.notify("handle-stop-charging", Event.wrap(message));
////		stopChargingProcessor.dispatch(message);
//
//	}
//
//	@Test
//	public void testProcessIn() throws Exception {
//
//	}
//
//	@Test
//	public void testProcessOut() throws Exception {
//
//	}
//}