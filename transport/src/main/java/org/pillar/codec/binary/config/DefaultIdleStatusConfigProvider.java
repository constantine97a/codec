package org.pillar.codec.binary.config;

import com.google.inject.Provider;

/**
 * Created by pillar on 2015/8/12.
 */
public class DefaultIdleStatusConfigProvider implements Provider<NettyIdleStatusConfig> {

    @Override
    public NettyIdleStatusConfig get() {
        return NettyIdleStatusConfig.newBuilder().build();
    }
}
