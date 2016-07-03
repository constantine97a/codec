package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.annotation.PF;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by pillar on 2015/8/25.
 */
public class IntFieldBuilderTest {

//
//    Class<?> clazz = ChargingFinInMessage.class;
//    Field f = clazz.getDeclaredField("chargingFinInItems");
//    System.out.println(((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]);
//
//
//    f = IntFieldBuilderTest.class.getDeclaredField("list");
//    System.out.println(((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]);

    @org.junit.Test
    public void test_IsCompatible() throws Exception {
//        IntFieldBuilder builder = new IntFieldBuilder();
//        boolean result = builder.isCompatible(Number.class, IntFieldBuilderTest.class.getDeclaredField("number").getAnnotation(F.class));
//        assertTrue(result);
//        UInt8PField uInt8PField = (UInt8PField) builder.build(Object.class, "number", IntFieldBuilderTest.class.getDeclaredField("number").getAnnotation(F.class));
//        assertNotNull(uInt8PField);
    }


    @PF(type = Integer.class, length = 8)
    private Number number;


}