package org.pillar.codec.binary.codec;

import com.google.common.base.Function;

/**
 * Created by Administrator on 2015/8/14.
 * 编码解码器
 */
public interface Codec<OUT, IN> {
    IN decode(OUT out);

    OUT encode(IN in);

    Function<OUT, IN> decoder();

    Function<IN, OUT> encoder();
}
