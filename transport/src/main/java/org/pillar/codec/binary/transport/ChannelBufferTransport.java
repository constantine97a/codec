package org.pillar.codec.binary.transport;

import org.pillar.codec.binary.exception.CTransportException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/13.
 */
public class ChannelBufferTransport extends Transport {

    private static final int DEFAULT_OUTPUT_BUFFER_SIZE = 1024;
    private static final int MAX_OUTPUT_BUFFER_SIZA = Integer.MAX_VALUE;
    private ByteBuf out;
    private ByteBuf in;
    private SocketChannel channel;


    public ChannelBufferTransport(SocketChannel channel, ByteBuf in, int outputMinimumSize) {
        checkNotNull(channel);
        checkNotNull(in);
        checkArgument(outputMinimumSize > 0, "the output minim size is less than zere!");
        outputMinimumSize = Math.min(DEFAULT_OUTPUT_BUFFER_SIZE, outputMinimumSize);
        this.channel = channel;
        this.in = in;
        this.out = Unpooled.buffer(outputMinimumSize, MAX_OUTPUT_BUFFER_SIZA).order(in.order());
    }


    public ChannelBufferTransport(SocketChannel channel, ByteBuf in, ByteBuf out) {
        this.channel = channel;
        this.in = in;
        this.out = out;
    }

    public ChannelBufferTransport(SocketChannel channel, ByteBuf in) {
        this(channel, in, DEFAULT_OUTPUT_BUFFER_SIZE);

    }


    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }

    @Override
    public void open() throws CTransportException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public int read(byte[] bytes, int offset, int len) throws CTransportException {
        int _read = Math.min(in.readableBytes(), len);
        in.readBytes(bytes, offset, _read);
        return _read;
    }

    @Override
    public ByteBuf read(int len) throws CTransportException {
        int _read = Math.min(in.readableBytes(), len);
        ByteBuf buf = in.readSlice(_read).retain();
        return buf;
    }

    @Override
    public int leftByteCount() {
        return in.readableBytes();
    }

    @Override
    public int readUInt16() {
        return in.readUnsignedShort();
    }

    @Override
    public long readUInt32() {
        return in.readUnsignedInt();
    }

    @Override
    public char readChar() {
        return (char) in.readByte();
    }

    @Override
    public String readString(Charset charset, int length) throws CTransportException {
        byte[] bytes = new byte[length];
        this.read(bytes, 0, length);
        return new String(bytes, charset);
    }

    @Override
    public int writeString(String string, Charset charset) throws CTransportException {
        byte[] bytes = string.getBytes(charset);
        this.write(bytes, 0, bytes.length);
        return bytes.length;
    }

    @Override
    public void writeUInt16(int uint16) {
        this.out.writeShort(uint16);
    }

    @Override
    public void writeUInt32(long uint32) {
        this.out.writeInt((int) uint32);
    }

    @Override
    public void writeChar(char c) {
        this.out.writeByte((byte) c);
    }

    @Override
    public void write(byte[] buf, int off, int len) throws CTransportException {
        out.writeBytes(buf, off, len);
    }

    @Override
    public void write(ByteBuf buf) {
        try {
            int writableBytes = buf.readableBytes();
            out.writeBytes(buf, writableBytes);
        } finally {
            ReferenceCountUtil.release(buf);
        }
    }

    public ByteBuf getOut() {
        return out;
    }

    public ByteBuf getIn() {
        return in;
    }

    @Override
    public ByteBuf getBuffer() {
        return getIn();
    }

    @Override
    public int getBytesRemainingInBuffer() {
        return getIn().readableBytes();
    }

    @Override
    public int getBufferPosition() {
        return getIn().readerIndex();
    }


}
