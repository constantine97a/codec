package org.pillar.codec.binary.transport;

import org.pillar.codec.binary.exception.CTransportException;
import io.netty.buffer.ByteBuf;

import java.io.Closeable;
import java.nio.charset.Charset;

/**
 * Created by pillar on 2015/8/13.
 * author:yincc
 * 代表协议的点
 */
public abstract class Transport implements Closeable {


	/**
	 * Queries whether the transport is open.
	 *
	 * @return True if the transport is open.
	 */
	public abstract boolean isOpen();

	/**
	 * Is there more data to be read?
	 *
	 * @return True if the remote side is still alive and feeding us
	 */
	public boolean peek() {
		return isOpen();
	}

	/**
	 * Opens the transport for reading/writing.
	 *
	 * @throws CTransportException if the transport could not be opened
	 */
	public abstract void open()
			throws CTransportException;

	/**
	 * Closes the transport.
	 */
	public abstract void close();

	/**
	 * Reads up to len bytes into buffer buf, starting at offset off.
	 *
	 * @param buf Array to read into
	 * @param off Index to start reading at
	 * @param len Maximum number of bytes to read
	 * @return The number of bytes actually read
	 * @throws CTransportException if there was an error reading data
	 */
	public abstract int read(byte[] buf, int off, int len)
			throws CTransportException;


	/**
	 *
	 * @param length
	 * @return
	 * @throws CTransportException
	 */
	public abstract ByteBuf read(int length) throws CTransportException;


	/**
	 * Get Underlying IN Bytebuf Readable Bytes Count
	 *
	 * @return Underlying IN ByteBuf Readable Bytes Count
	 * invoke in.readableBytes()
	 */
	public abstract int leftByteCount();

	/**
	 * Guarantees that all of len bytes are actually read off the transport.
	 *
	 * @param buf Array to read into
	 * @param off Index to start reading at
	 * @param len Maximum number of bytes to read
	 * @return The number of bytes actually read, which must be equal to len
	 * @throws CTransportException if there was an error reading data
	 */
	public int readAll(byte[] buf, int off, int len)
			throws CTransportException {
		int got = 0;
		int ret = 0;
		while (got < len) {
			ret = read(buf, off + got, len - got);
			if (ret <= 0) {
				throw new CTransportException(
						"Cannot read. Remote side has closed. Tried to read "
								+ len
								+ " bytes, but only got "
								+ got
								+ " bytes. (This is often indicative of an internal error on the server side. Please check your server logs.)");
			}
			got += ret;
		}
		return got;
	}

	/**
	 * 读无符号整数
	 *
	 * @return integer
	 */
	public abstract int readUInt16();

	public abstract long readUInt32();

	public abstract char readChar();

	public abstract String readString(Charset charset, int length) throws CTransportException;

	public abstract int writeString(String string, Charset charset) throws CTransportException;

	/**
	 * Writes the buffer to the output
	 *
	 * @param buf The output data buffer
	 * @throws CTransportException if an error occurs writing data
	 */
	public void write(byte[] buf) throws CTransportException {
		write(buf, 0, buf.length);
	}


	public abstract void writeUInt16(int uint16);

	public abstract void writeUInt32(long uint32);

	public abstract void writeChar(char c);


	/**
	 * Writes up to len bytes from the buffer.
	 *
	 * @param buf The output data buffer
	 * @param off The offset to start writing from
	 * @param len The number of bytes to write
	 * @throws CTransportException if there was an error writing data
	 */
	public abstract void write(byte[] buf, int off, int len)
			throws CTransportException;

	public abstract void write(ByteBuf buf);

	/**
	 * Flush any pending data out of a transport buffer.
	 *
	 * @throws CTransportException if there was an error writing out data.
	 */
	public void flush()
			throws CTransportException {
	}

	/**
	 * Access the protocol's underlying buffer directly. If this is not a
	 * buffered transport, return null.
	 *
	 * @return protocol's Underlying buffer
	 */
	public abstract ByteBuf getBuffer();

	public abstract ByteBuf getOut();


	public abstract ByteBuf getIn();

	/**
	 * Return the index within the underlying buffer that specifies the next spot
	 * that should be read from.
	 *
	 * @return index within the underlying buffer that specifies the next spot
	 * that should be read from
	 */
	public int getBufferPosition() {
		return 0;
	}

	/**
	 * Get the number of bytes remaining in the underlying buffer. Returns -1 if
	 * this is a non-buffered transport.
	 *
	 * @return the number of bytes remaining in the underlying buffer. <br> Returns -1 if
	 * this is a non-buffered transport.
	 */
	public int getBytesRemainingInBuffer() {
		return -1;
	}


}
