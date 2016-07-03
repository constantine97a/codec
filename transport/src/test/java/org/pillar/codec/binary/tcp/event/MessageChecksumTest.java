package org.pillar.codec.binary.event;

import org.junit.Test;
import org.pillar.codec.binary.event.MessageChecksum;

/**
 * Created by pillar on 2015/8/14.
 */
public class MessageChecksumTest {

    @Test
    public void test_checksm() throws Exception {
        MessageChecksum checksum = new MessageChecksum();
        checksum.setChecksum(0xFd);
        System.out.println(checksum.toString());

    }
}