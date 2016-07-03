package org.pillar.codec.binary.codec;

import com.google.common.base.Function;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by pillar on 2015/10/22.
 */
public abstract class AbstractCodec<OUT, IN> implements Codec<OUT, IN> {
    /**
     * 解码 从二进制流转化为对象
     *
     * @param out
     * @return
     */
    public IN decode(OUT out) {
        try {
            return decoder().apply(out);
        } finally {
            ReferenceCountUtil.release(out);
        }
    }


    /**
     * 编码 从对象转成二进制
     *
     * @param in Object
     * @return Out--> ByteBuf
     */
    public OUT encode(IN in) {
        return encoder().apply(in);
    }

    public abstract Function<OUT, IN> decoder();

    public abstract Function<IN, OUT> encoder();
}
