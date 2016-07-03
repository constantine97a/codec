package org.pillar.codec.binary.transport;

import org.pillar.codec.binary.builder.ProtocolFieldBuilderWrapper;
import org.pillar.codec.binary.schema.CompositeField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/14.
 */
@Component
public class DuplexProtocolFactory implements ProtocolFactory {


	ProtocolFieldBuilderWrapper protocolFieldBuilderWrapper;

	@Autowired
	public DuplexProtocolFactory(ProtocolFieldBuilderWrapper protocolFieldBuilderWrapper) {
		this.protocolFieldBuilderWrapper = protocolFieldBuilderWrapper;
	}

	@Override
	public <T> Protocol getInProtocol(Class<T> type) {
		CompositeField def = protocolFieldBuilderWrapper.getInProtocolCompositedField(type);
		return new Protocol(def);
	}

	@Override
	public <T> Protocol getOutProtocol(T value, Class<T> type) {
		CompositeField def = protocolFieldBuilderWrapper.getOutProtocolCompositedField(value, type);
		checkNotNull(def);
		return new Protocol(def);
	}
}
