package org.pillar.codec.binary.schema;

import org.junit.Test;
import org.pillar.codec.binary.schema.CompositeField;
import org.pillar.codec.binary.schema.UInt16PField;
import org.pillar.codec.binary.schema.UInt32PField;
import org.pillar.codec.binary.schema.UInt8PField;

import java.util.List;

/**
 * Created by pillar on 2015/8/17.
 */
public class CompositeFieldTest {
    private CompositeField compositeField;

    @Test
    public void test_toString() throws Exception {
        CompositeField compositeField = new CompositeField("value", List.class);
        compositeField.addField(new UInt16PField("field1"));
        compositeField.addField(new UInt8PField("field2"));
        compositeField.addField(new UInt32PField("field3"));

        CompositeField newCom = new CompositeField("value", List.class);
        newCom.addField(new UInt16PField("field1"));
        newCom.addField(new UInt8PField("field2"));
        newCom.addField(new UInt32PField("field3"));

        compositeField.addField(newCom);

        System.out.println(compositeField);
    }
}