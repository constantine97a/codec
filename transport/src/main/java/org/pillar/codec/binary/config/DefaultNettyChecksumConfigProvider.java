package org.pillar.codec.binary.config;


import javax.inject.Provider;

/**
 * Created by pillar on 2015/8/14.
 */
public class DefaultNettyChecksumConfigProvider implements Provider<NettyCheckFrameConfig> {


    @Override
    public NettyCheckFrameConfig get() {
        return NettyCheckFrameConfig.newBuild().build();
    }
}
