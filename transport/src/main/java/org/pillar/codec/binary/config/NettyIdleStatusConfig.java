package org.pillar.codec.binary.config;

import com.google.inject.ProvidedBy;

import java.util.concurrent.TimeUnit;

/**
 * Created by pillar on 2015/8/12.
 */
@ProvidedBy(DefaultIdleStatusConfigProvider.class)
public class NettyIdleStatusConfig {

    private final int readIdleTime;
    private final int writeIdleTime;
    private final int allIdleTime;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public NettyIdleStatusConfig(int readIdleTime, int writeIdleTime, int allIdleTime) {
        this.readIdleTime = readIdleTime;
        this.writeIdleTime = writeIdleTime;
        this.allIdleTime = allIdleTime;
    }

    public NettyIdleStatusConfig(int readIdleTime, int writeIdleTime, int allIdleTime, TimeUnit timeUnit) {
        this.readIdleTime = readIdleTime;
        this.writeIdleTime = writeIdleTime;
        this.allIdleTime = allIdleTime;
        this.timeUnit = timeUnit;
    }


    public int getReadIdleTime() {
        return readIdleTime;
    }

    public int getWriteIdleTime() {
        return writeIdleTime;
    }

    public int getAllIdleTime() {
        return allIdleTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }


    /**
     * @return
     */
    public static NettyIdleStatusConfigBuilder newBuilder() {
        return new NettyIdleStatusConfigBuilder();
    }

}
