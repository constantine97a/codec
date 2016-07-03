package org.pillar.codec.binary.codec;

import com.google.common.base.Function;
import io.netty.buffer.ByteBuf;

/**
 * Created by pillar on 2015/8/15.
 */
public class DummyCodec  implements Codec<ByteBuf, Void> {


    @Override
    public Void decode(ByteBuf byteBuf) {
        return null;
    }

    @Override
    public ByteBuf encode(Void aVoid) {
        return null;
    }

    @Override
    public Function<ByteBuf, Void> decoder() {
        return null;
    }

    @Override
    public Function<Void, ByteBuf> encoder() {
        return null;
    }
}
