package org.pillar.codec.binary.schema;

import org.junit.Test;
import org.pillar.codec.binary.schema.UInt8PField;

import static org.junit.Assert.*;

/**
 * Created by pillar on 2015/8/19.
 */
public class UInt8PFieldTest {

    @Test
    public void testCreateField() throws Exception {
        UInt8PField uInt8PField = new UInt8PField("value");
        assertEquals("uint8[name:value,byte]", uInt8PField.toString());
        System.out.println(uInt8PField.toString());

    }

    @Test
    public void testToString() throws Exception {

    }
}