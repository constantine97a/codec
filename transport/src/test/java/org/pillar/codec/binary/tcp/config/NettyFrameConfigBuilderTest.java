//package org.pillar.codec.binary.config;
//
//import com.pillar.pile.tools.utils.PropertiesLoader;
//import org.junit.Test;
//import org.pillar.codec.binary.config.NettyFrameConfig;
//import org.pillar.codec.binary.config.NettyFrameConfigBuilder;
//
//import static org.junit.Assert.assertNotNull;
//
///**
// * Created by pillar on 2015/8/12.
// */
//public class NettyFrameConfigBuilderTest {
//
//
//    @Test
//    public void test_frame_config() throws Exception {
//
//        PropertiesLoader propertiesLoader = new PropertiesLoader("transport-default.properties");
//        NettyFrameConfigBuilder nettyFrameConfigBuilder = NettyFrameConfig.newBuilder();
//        NettyFrameConfig nettyFrameConfig = nettyFrameConfigBuilder.build();
//        assertNotNull(nettyFrameConfig);
//        System.out.println(nettyFrameConfig);
//
//
//    }
//}