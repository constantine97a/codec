package org.pillar.codec.binary.config;

import org.apache.commons.collections.ExtendedProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by pillar on 2015/8/12.
 */
public class NettyIdleStatusConfigBuilder extends AbastractConfigBuilder {
    public NettyIdleStatusConfig build() {
        ExtendedProperties extendedProperties = this.getProperties();

        int readIdleTime = extendedProperties.getInt("idle.readerIdleTime");
        int writeIdleTime = extendedProperties.getInt("idle.writerIdleTime");
        int allIdleTime = extendedProperties.getInt("idle.allIdleTime");
        TimeUnit timeUnit = TimeUnit.valueOf(extendedProperties.getString("idle.timeUnit", TimeUnit.SECONDS.name()));

        return new NettyIdleStatusConfig(readIdleTime, writeIdleTime, allIdleTime, timeUnit);

    }
}
