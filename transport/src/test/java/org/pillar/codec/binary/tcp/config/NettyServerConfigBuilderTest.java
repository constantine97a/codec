package org.pillar.codec.binary.config;

import org.junit.Test;
import org.pillar.codec.binary.config.NettyServerConfig;
import org.pillar.codec.binary.config.NettyServerConfigBuilder;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pillar on 2015/8/12.
 */
public class NettyServerConfigBuilderTest {

    private NettyServerConfigBuilder nettyServerConfigBuilder;


    @Test
    public void testBuild() throws Exception {
        NettyServerConfig config = NettyServerConfig.newBuilder().build();
        assertNotNull(config);
        System.out.println(config);
    }
}