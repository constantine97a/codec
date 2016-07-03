package org.pillar.codec.binary.core;

import org.pillar.codec.binary.codec.Codec;
import org.pillar.codec.binary.exception.CTransportException;
import org.pillar.codec.binary.schema.PField;
import org.pillar.codec.binary.transport.Protocol;
import org.pillar.codec.binary.transport.Transport;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/17.
 */
public class ProtocolStreamReader implements StreamReader {

	private final Stack<Pointer> pointers = new Stack<>(1024);

	private final Logger logger = LoggerFactory.getLogger(ProtocolStreamReader.class);


	private Transport transport;

	private Protocol protocol;

	private PField currentPField;


	public ProtocolStreamReader(Transport transport, Protocol protocol) {
		checkNotNull(transport);
		checkNotNull(protocol);
		this.transport = transport;
		this.protocol = protocol;
		currentPField = this.protocol.currentField();
		pointers.push(new Pointer());

	}

	protected void reassignCurrentElement(PField current) {
		this.currentPField = current;
	}


	@Override
	public void moveDown() {
		final Pointer pointer = pointers.peek();
		pointers.push(new Pointer());
		currentPField = getChild(pointer.v);
		pointer.v++;
		reassignCurrentElement(currentPField);
	}

	@Override
	public void moveUp() {
		currentPField = getParent();
		pointers.popSilently();
		reassignCurrentElement(currentPField);
	}

	/**
	 * @return meet the end
	 */
	@Override
	public boolean hasMoreChildren() {
		final Pointer pointer = pointers.peek();
		if (pointer.v < getChildCount()) {
			return true;
		} else {
			return false;
		}
	}

	public PField getParent() {
		return currentPField.getParent();
	}


	public PField getChild(int index) {
		return currentPField.getChild(index);
	}

	public int getChildCount() {
		return currentPField.getChildCount();
	}

	@Override
	public Object readValue() {
		try {
			return this.transport.read(currentPField.getLength());
		} catch (CTransportException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e);
		}
	}


	public PField getCurrentField() {
		return currentPField;
	}

	@Override
	public String getCurrentNodeName() {
		return getCurrentField().getName();
	}

	@Override
	public Class<?> getCurrentNodeClazz() {
		return getCurrentField().getClazz();
	}

	@Override
	public void close() {
		logger.warn("Closing the Reader ,But Is Useless,No Egg Use");
	}

	@Override
	public StreamReader underlyingReader() {
		return this;
	}

	@Override
	public Codec getCurrentCodec() {
		return getCurrentField().getCodec();
	}

	private static class Pointer {
		public int v;
	}


}
