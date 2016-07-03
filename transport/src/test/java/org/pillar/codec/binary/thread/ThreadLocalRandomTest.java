package org.pillar.codec.binary.thread;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by pillar on 2015/9/18.
 */
public class ThreadLocalRandomTest {
	@Test
	public void test_thread_local_random() throws Exception {
		for (int i = 0; i < 1024; i++) {
			long value = ThreadLocalRandom.current().nextLong();
			System.out.println(value);
		}


	}
}
