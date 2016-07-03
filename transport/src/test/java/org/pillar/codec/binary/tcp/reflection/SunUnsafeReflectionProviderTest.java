//package org.pillar.codec.binary.reflection;
//
//import org.pillar.codec.binary.event.FixTimedChargingInMessage;
//import com.pillar.pile.tools.utils.Reflections;
//import org.junit.Before;
//import org.junit.Test;
//import org.pillar.codec.binary.reflection.SunUnsafeReflectionProvider;
//
//import java.util.ArrayList;
//import java.util.Date;
//
///**
// * Created by pillar on 2015/9/17.
// */
//public class SunUnsafeReflectionProviderTest {
//
//	private SunUnsafeReflectionProvider provider;
//
//	@Before
//	public void setUp() throws Exception {
//		provider = new SunUnsafeReflectionProvider();
//
//	}
//
//	@Test
//	public void testWriteField() throws Exception {
//		FixTimedChargingInMessage fixTimedChargingInMessage = new FixTimedChargingInMessage();
//		fixTimedChargingInMessage.setCdzCode(1024);
//		fixTimedChargingInMessage.setFactoryId(1);
//		fixTimedChargingInMessage.setCurrentDate(new Date());
//		fixTimedChargingInMessage.setFixTimedChargingGuns(new ArrayList<FixTimedChargingInMessage.FixTimedChargingGun>());
//		System.out.println(provider.readField(fixTimedChargingInMessage, "cdzCode", FixTimedChargingInMessage.class));
//		System.out.println(provider.readField(fixTimedChargingInMessage, "factoryId", FixTimedChargingInMessage.class));
//		System.out.println(provider.readField(fixTimedChargingInMessage, "currentDate", FixTimedChargingInMessage.class));
//		System.out.println(provider.readField(fixTimedChargingInMessage, "fixTimedChargingGuns", FixTimedChargingInMessage.class));
//
////		long start = System.nanoTime();
////		System.out.println(provider.readField(fixTimedChargingInMessage, "cdzCode", FixTimedChargingInMessage.class));
////		long end = System.nanoTime();
////		System.out.printf("===========1:" + (end - start)+"\n");
////		start = System.nanoTime();
////		System.out.println(Reflections.getFieldValue(fixTimedChargingInMessage, "cdzCode"));
////		end = System.nanoTime();
////		System.out.println("===========2:" + (end - start)+"\n");
//
//	}
//
//	@Test
//	public void testReadField() throws Exception {
//
//	}
//
//	@Test
//	public void testInit() throws Exception {
//
//	}
//}