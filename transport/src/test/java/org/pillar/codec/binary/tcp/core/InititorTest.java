package org.pillar.codec.binary.core;

import org.pillar.codec.binary.event.MessageHead;
import org.pillar.codec.binary.event.Value;
import org.pillar.codec.binary.transport.ChannelBufferTransport;
import org.pillar.codec.binary.transport.Protocol;
import org.pillar.codec.binary.transport.Transport;
import io.netty.buffer.Unpooled;
import junit.framework.Assert;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.pillar.codec.binary.core.ProtocolStreamReader;
import org.pillar.codec.binary.schema.*;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by pillar on 2015/8/18.
 */
public class InititorTest {

    private ProtocolStreamReader protocolStreamReader;


    private Transport transport;


    private Protocol protocol;


    private Initiator inititor;
    private CompositeField header;

    private CompositeField body;


    @Before
    public void setUp() throws Exception {

        header = new CompositeField("head", MessageHead.class);
        header.addField(new StartPField("start"));
        header.addField(new LengthPField("length"));
        header.addField(new VersionPField("version"));
        header.addField(new SequencePField("sequence"));
        header.addField(new CommandPField("command"));

        inititor = new Initiator();


        body = new CompositeField("body", TestMessageBody.class);
        body.addField(new UInt8PField("value"));
        body.addField(new StringPField("stringValue", 2));

        CompositeField list = new CompositeField("values", List.class);
        CompositeField item = new CompositeField("", Value.class);
        item.addField(new UInt8PField("v"));
        item.addField(new StringPField("b", 2));

        list.addField(item);
        list.addField(item);
        body.addField(list);


        CompositeField list2 = new CompositeField("values2", List.class);
        list2.addField(item);
        list2.addField(item);

        body.addField(list2);


    }

    @Test
    public void testHead() throws Exception {


        byte[] bytes = Hex.decodeHex("FAFB3040FB50E91213".toCharArray());

        protocol = new Protocol(header);

        transport = new ChannelBufferTransport(null, Unpooled.copiedBuffer(bytes), null);

        MessageHead h = inititor.from(transport, protocol);

        assertNotNull(h);

        System.out.println(h.toString());

    }


    @Test
    public void test_test_composite_protocol() throws Exception {

        byte[] bytes = Hex.decodeHex("FA6162006162306162406162506162".toCharArray());

        transport = new ChannelBufferTransport(null, Unpooled.copiedBuffer(bytes), null);

        protocol = new Protocol(body);

        TestMessageBody messageBody = inititor.from(transport, protocol);

        Assert.assertNotNull(messageBody);

        System.out.printf("fff" + messageBody.toString());


    }


    @Test
    public void test_write_header() {
        MessageHead h = new MessageHead();
        h.setStart("faf5");
        h.setLength(4);
        h.setCommand(81);
        h.setSequence(10);
        h.setVersion(80);

        transport = new ChannelBufferTransport(null, null, Unpooled.buffer(1024));

        protocol = new Protocol(header);

        inititor.to(h, transport, protocol);

        System.out.println(transport);

    }


    @Test
    public void test_write_body() throws Exception {
        body = new CompositeField("body", TestMessageBody.class);
        body.addField(new UInt8PField("value"));
        body.addField(new StringPField("stringValue", 2));

        CompositeField list = new CompositeField("values", List.class);
        CompositeField item = new CompositeField("", Value.class);
        item.addField(new UInt8PField("v"));
        item.addField(new StringPField("b", 2));

        list.addField(item);
        list.addField(item);
        body.addField(list);


        CompositeField list2 = new CompositeField("values2", List.class);
        list2.addField(item);
        list2.addField(item);

        body.addField(list2);

        transport = new ChannelBufferTransport(null, null, Unpooled.buffer(1024));

        protocol = new Protocol(body);


        TestMessageBody messageBody = new TestMessageBody();
        messageBody.setValue(80);
        messageBody.setStringValue("st");


        final Value value = new Value();
        value.setB("bb");
        value.setV(10);

        List<Value> values = new ArrayList<Value>() {{
            this.add(value);
            this.add(value);
        }};

        messageBody.setValues(values);
        messageBody.setValues2(values);

        inititor.to(messageBody, transport, protocol);
        System.out.println(transport);


    }
}