package org.pillar.codec.binary.codec;

import io.netty.buffer.ByteBuf;
import org.junit.Test;
import org.pillar.codec.binary.codec.StringCodec;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by pillar on 2015/8/19.
 */
public class StringCodecTest {
	@Test
	public void test_codec() throws Exception {
		StringCodec codec = new StringCodec(16);
		ByteBuf byteBuf = codec.encode("abcdab");
		String value = codec.decode(byteBuf);
        assertEquals("abcdab", value);
		byteBuf = codec.encode(value);
		value = codec.decode(byteBuf);
		System.out.println(value);
	}
}