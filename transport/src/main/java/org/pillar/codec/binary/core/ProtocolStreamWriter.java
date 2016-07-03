package org.pillar.codec.binary.core;

import org.pillar.codec.binary.codec.Codec;
import org.pillar.codec.binary.schema.PField;
import org.pillar.codec.binary.transport.Protocol;
import org.pillar.codec.binary.transport.Transport;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/18.
 * 协议流Writer
 */
public class ProtocolStreamWriter implements StreamWriter {

	private final Stack<Pointer> pointers = new Stack<>(1024);


	private final Logger logger = LoggerFactory.getLogger(ProtocolStreamWriter.class);

	private Transport transport;

	private Protocol protocol;


	private PField currentPField;


	public ProtocolStreamWriter(Transport transport, Protocol protocol) {
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
	public boolean hasMoreChildren() {
		final Pointer pointer = pointers.peek();
		if (pointer.v < getChildCount()) {
			return true;
		} else {
			return false;
		}
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

	@Override
	public String getCurrentNodeName() {
		return getCurrentPField().getName();
	}

	@Override
	public void writeValue(Object value) {
		this.transport.write((ByteBuf) value);
	}

	@Override
	public Class<?> getCurrentNodeClazz() {
		return getCurrentPField().getClazz();
	}


	@Override
	public StreamWriter underlyingWriter() {
		return this;
	}

	@Override
	public Codec getCurrentCodec() {
		return getCurrentPField().getCodec();
	}

	@Override
	public void close() throws IOException {
		getLogger().warn("Closing Writer Is Useless,No Egg Use!");
	}

	public PField getCurrentPField() {
		return currentPField;
	}

	public PField getParent() {
		return currentPField.getParent();
	}

	protected PField getChild(int index) {
		return currentPField.getChild(index);
	}

	protected int getChildCount() {
		return currentPField.getChildCount();
	}

	private static class Pointer {
		public int v;
	}

	public Logger getLogger() {
		return logger;
	}
}
