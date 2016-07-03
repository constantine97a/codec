package org.pillar.codec.binary.config;

import com.google.inject.Provider;

/**
 * Created by pillar on 2015/8/10.
 */

public class DefaultNettyServerConfigProvider  implements Provider<NettyServerConfig> {

    @Override
    public NettyServerConfig get() {
        return NettyServerConfig.newBuilder().build();
    }
}
