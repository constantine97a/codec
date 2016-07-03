package org.pillar.codec.binary.assembly;

import org.junit.Test;

/**
 * Created by pillar on 2015/10/8.
 */
public class HexTest {

    @Test
    public void test_hex() throws Exception {
        System.out.println(258 & 0xFF);
        System.out.println((258 >>> 8) & 0xFF);
    }
}
