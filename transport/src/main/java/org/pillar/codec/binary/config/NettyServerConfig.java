package org.pillar.codec.binary.config;

import com.google.inject.ProvidedBy;

import java.nio.ByteOrder;
import java.util.concurrent.ThreadFactory;

/**
 * Created by pillar on 2015/8/10.
 * netty 服务器的配置
 */
@ProvidedBy(DefaultNettyServerConfigProvider.class)
public class NettyServerConfig {
    /**
     * boss 线程数
     */
    private final int bossThreadCount;

    /**
     * worker thread count
     */
    private final int workerThreadCount;

    /**
     * tcp back log count
     */
    private final int tcpBackLogCount;

    /**
     * 线程
     */
    private final boolean keepAlive;

    /**
     * tcp no delay
     */
    private final boolean tcpNoDelay;

    /**
     * recieve Buffer Size
     */
    private final int revBufferSize;

    /**
     * write buffer size
     */
    private final int wrtBufferSize;

    /**
     * thread count per cpu
     */
    private final int threadPerCpu;

    /**
     * max thread limit
     */
    private final int maxThreadLimit;

    /**
     * availiable
     */
    private final int availableWorkerCount;

    private final ThreadFactory bossThreadFactory;

    private final ThreadFactory workerThreadFactory;


    private final int port;
    private final ByteOrder byteOrder;

    public NettyServerConfig(int port, int bossThreadCount,
                             int workerThreadCount,
                             int tcpBackLogCount,
                             boolean keepAlive,
                             boolean tcpNoDelay,
                             int revBufferSize,
                             int wrtBufferSize,
                             int threadPerCpu,
                             int maxThreadLimit,
                             ThreadFactory bossThreadFactory,
                             ThreadFactory workerThreadFactory,
                             ByteOrder byteOrder) {
        this.bossThreadCount = bossThreadCount;
        this.workerThreadCount = workerThreadCount;
        this.tcpBackLogCount = tcpBackLogCount;
        this.keepAlive = keepAlive;
        this.tcpNoDelay = tcpNoDelay;
        this.revBufferSize = revBufferSize;
        this.wrtBufferSize = wrtBufferSize;
        this.threadPerCpu = threadPerCpu;
        this.maxThreadLimit = maxThreadLimit;

        this.bossThreadFactory = bossThreadFactory;
        this.workerThreadFactory = workerThreadFactory;
        this.port = port;
        this.byteOrder = byteOrder;


        int threadCount = Runtime.getRuntime().availableProcessors() * threadPerCpu;
        availableWorkerCount = threadCount >
                maxThreadLimit ? maxThreadLimit : threadCount;
    }

    public int getBossThreadCount() {
        return bossThreadCount;
    }

    public int getWorkerThreadCount() {
        return workerThreadCount;
    }

    public int getTcpBackLogCount() {
        return tcpBackLogCount;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public int getRevBufferSize() {
        return revBufferSize;
    }

    public int getWrtBufferSize() {
        return wrtBufferSize;
    }

    public static NettyServerConfigBuilder newBuilder() {
        return new NettyServerConfigBuilder();
    }

    public int getThreadPerCpu() {
        return threadPerCpu;
    }


    public int getMaxThreadLimit() {
        return maxThreadLimit;
    }


    public ThreadFactory getBossThreadFactory() {
        return bossThreadFactory;
    }


    public ThreadFactory getWorkerThreadFactory() {
        return workerThreadFactory;
    }



    @Override
    public String toString() {
        return "NettyServerConfig{" +
                "bossThreadCount=" + bossThreadCount +
                ", workerThreadCount=" + workerThreadCount +
                ", tcpBackLogCount=" + tcpBackLogCount +
                ", keepAlive=" + keepAlive +
                ", tcpNoDelay=" + tcpNoDelay +
                ", revBufferSize=" + revBufferSize +
                ", wrtBufferSize=" + wrtBufferSize +
                ", threadPerCpu=" + threadPerCpu +
                ", maxThreadLimit=" + maxThreadLimit +
                ", availableWorkerCount=" + availableWorkerCount +
                ", bossThreadFactory=" + bossThreadFactory +
                ", workerThreadFactory=" + workerThreadFactory +
                ", port=" + port +
                ", byteOrder=" + byteOrder +
                '}';
    }

    public int getAvailableWorkerCount() {
        return availableWorkerCount;
    }

    public int getPort() {
        return port;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }
}
