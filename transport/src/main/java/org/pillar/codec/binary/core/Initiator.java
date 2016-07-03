package org.pillar.codec.binary.core;

import org.pillar.codec.binary.transport.Protocol;
import org.pillar.codec.binary.transport.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/17.
 * 解析流程启动者
 * <p/>
 * 1 解析头，
 * 2 解析 消息体
 * 3
 */

@Component
public class Initiator extends AbstractInitiator {

	private final Logger logger = LoggerFactory.getLogger(Initiator.class);


	public Initiator() {
		super();
		setUpConverter();
	}


	/**
	 * from
	 *
	 * @param transport transport
	 * @param protocol  protocal
	 * @param <T>       result type
	 * @return
	 */
	public <T> T from(Transport transport, Protocol protocol) {
		getLogger().debug("UnMarshalling:{},{},{},{}", transport, protocol);
		checkNotNull(transport);
		checkNotNull(protocol);
		protocol = protocol.padding(transport.getBuffer().readableBytes());
		StreamReader reader = createStreamReader(transport, protocol);
		Unmarshaller unmarshaller = createUnmarshallingContext(reader, protocol.getType());
		return (T) unmarshaller.start();
	}


	/**
	 * 将对象转化为ByteBuf
	 *
	 * @param transport transport 底层的数据通道
	 * @param protocol  protocol 协议
	 */
	public void to(Object object, Transport transport, Protocol protocol) {
		getLogger().debug("Marshalling:{},{},{},{}", object, transport, protocol);
		checkNotNull(transport);
		checkNotNull(protocol);
		StreamWriter writer = createStreamWriter(transport, protocol);
		Marshaller marshaller = createMarshallingContext(object, writer, protocol.getType());
		marshaller.start();

	}

	private Marshaller createMarshallingContext(Object root, StreamWriter writer, Class<?> clazz) {
		return new Marshaller(root, writer, clazz, converterLookup, mapper);
	}

	private StreamWriter createStreamWriter(Transport transport, Protocol protocol) {
		return new ProtocolStreamWriter(transport, protocol);
	}

	private Unmarshaller createUnmarshallingContext(StreamReader reader, Class<?> clazz) {
		return new Unmarshaller(null, reader, clazz, converterLookup, mapper);
	}

	private StreamReader createStreamReader(Transport transport, Protocol protocol) {
		return new ProtocolStreamReader(transport, protocol);
	}

	public Logger getLogger() {
		return logger;
	}
}
