package org.pillar.codec.binary.core;

import org.pillar.codec.binary.event.MessageHead;
import org.pillar.codec.binary.transport.ChannelBufferTransport;
import org.pillar.codec.binary.transport.Protocol;
import org.pillar.codec.binary.transport.Transport;
import io.netty.buffer.Unpooled;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.pillar.codec.binary.core.ProtocolStreamReader;
import org.pillar.codec.binary.schema.*;

/**
 * Created by pillar on 2015/8/17.
 */
public class ProtocolStreamReaderTest {

    private ProtocolStreamReader protocolStreamReader;


    private Transport transport;


    private Protocol protocol;

    @Before
    public void setUp() throws Exception {


        CompositeField header = new CompositeField("head", MessageHead.class);
        header.addField(new StartPField("start"));
        header.addField(new LengthPField("length"));
        header.addField(new VersionPField("version"));
        header.addField(new SequencePField("sequence"));
        header.addField(new CommandPField("command"));

//        CompositeField body = new CompositeField("body");
//
//        body.addField(new UInt8PField("data"));
//
//
//        CompositeField checkSum = new CompositeField("checksum");
//        checkSum.addField(new UInt8PField("checksum"));
//
//
//        CompositeField message = new CompositeField("");
//        message.addField(header, body, checkSum);


        byte[] bytes = Hex.decodeHex("FAF50400801030FAFB".toCharArray());

        protocol = new Protocol(header);

        transport = new ChannelBufferTransport(null, Unpooled.copiedBuffer(bytes), null);

        protocolStreamReader = new ProtocolStreamReader(transport, protocol);

        while (protocolStreamReader.hasMoreChildren()) {
            protocolStreamReader.moveDown();
            Object value = protocolStreamReader.readValue();
            System.out.println(protocolStreamReader.getCurrentField().getName() + ":" + value);
            protocolStreamReader.moveUp();
        }
        System.out.println(protocolStreamReader);

    }


    @Test
    public void test_juni() throws Exception {


    }
}