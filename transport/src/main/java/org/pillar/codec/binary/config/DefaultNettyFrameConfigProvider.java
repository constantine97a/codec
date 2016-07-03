package org.pillar.codec.binary.config;

import com.google.inject.Provider;

/**
 * Created by pillar on 2015/8/12.
 */
public class DefaultNettyFrameConfigProvider implements Provider<NettyFrameConfig> {

    public NettyFrameConfig get() {
        return NettyFrameConfig.newBuilder().build();
    }
}
