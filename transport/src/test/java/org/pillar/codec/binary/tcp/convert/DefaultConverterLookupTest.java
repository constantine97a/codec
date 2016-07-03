package org.pillar.codec.binary.convert;

import org.junit.Before;
import org.junit.Test;
import org.pillar.codec.binary.convert.DefaultConverterLookup;
import org.pillar.codec.binary.core.*;

/**
 * Created by pillar on 2015/8/18.
 */
public class DefaultConverterLookupTest {

    ConverterLookup converterLookup;

    @Before
    public void setUp() throws Exception {
        converterLookup = new DefaultConverterLookup();


    }

    @Test
    public void test_converter() throws Exception {

        ((ConverterRegistry) converterLookup).registerConverter(new Converter() {
            @Override
            public void marshal(Object source, StreamWriter writer, MarshallingContext context) {

            }

            @Override
            public Object unmarshal(StreamReader reader, UnmarshallingContext context) {
                return null;
            }

            @Override
            public boolean canConvert(Class<?> type) {
                return true;
            }
        }, 1);

        Converter converter = converterLookup.lookupConverterForType(String.class);
        converter.canConvert(String.class);
    }
}