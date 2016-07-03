package org.pillar.codec.binary.transport;

import org.pillar.codec.binary.builder.ProtocolFieldBuilderWrapper;
import org.pillar.codec.binary.event.MessageHead;
import org.junit.Test;
import org.pillar.codec.binary.transport.DuplexProtocolFactory;
import org.pillar.codec.binary.transport.Protocol;

import static org.junit.Assert.*;

/**
 * Created by pillar on 2015/8/20.
 */
public class DuplexProtocolFactoryTest {
    @Test
    public void test_init() throws Exception {
        DuplexProtocolFactory factory = new DuplexProtocolFactory(new ProtocolFieldBuilderWrapper());
        Protocol protocol = factory.getInProtocol(MessageHead.class);
        assertNotNull(protocol);
        System.out.println(protocol);

    }

    @Test
    public void test_() throws Exception {


    }
}