package org.pillar.codec.binary.config;

/**
 * Created by pillar on 2015/8/14.
 */
public class NettyCheckFrameConfig {
    private final int initialBytesToStrip;
    private final int checksumLength;

    public int getInitialBytesToStrip() {
        return initialBytesToStrip;
    }

    public int getChecksumLength() {
        return checksumLength;
    }

    public NettyCheckFrameConfig(int initialBytesToStrip, int checksumLength) {

        this.initialBytesToStrip = initialBytesToStrip;
        this.checksumLength = checksumLength;
    }


    public static NettyChecksumFrameConfigBuilder newBuild() {
        return new NettyChecksumFrameConfigBuilder();
    }
}
