package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.annotation.PF;
import org.pillar.codec.binary.builder.StringFieldBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by pillar on 2015/8/25.
 */
public class StringFieldBuilderTest {

    private StringFieldBuilder stringFieldBuilder;

    @Before
    public void setUp() throws Exception {
        stringFieldBuilder = new StringFieldBuilder();

    }

    @PF(type = String.class, length = 8)
    private CharSequence charSequence;

    @Test
    public void testBuild() throws Exception {

    }

    @Test
    public void testIsCompatible() throws Exception {
//        Field field = StringFieldBuilderTest.class.getDeclaredField("charSequence");
//        boolean result = stringFieldBuilder.isCompatible(CharSequence.class, field.getAnnotation(F.class));
//        assertTrue(result);
//        StringPField stringPField = stringFieldBuilder.build(null, field.getName(), field.getAnnotation(F.class));
//        assertNotNull(stringPField);
    }
}