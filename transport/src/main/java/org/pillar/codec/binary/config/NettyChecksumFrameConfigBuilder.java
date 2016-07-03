package org.pillar.codec.binary.config;

import org.apache.commons.collections.ExtendedProperties;

/**
 * Created by pillar on 2015/8/14.
 */
public class NettyChecksumFrameConfigBuilder extends AbastractConfigBuilder {
    @Override
    public NettyCheckFrameConfig build() {
        ExtendedProperties extendedProperties = this.getProperties();
        int initialBytesToStrip = extendedProperties.getInt("protocal.checksum.bytestrip");

        int checksumLength = extendedProperties.getInt("protocal.checksum.bytecount");

        return new NettyCheckFrameConfig(initialBytesToStrip, checksumLength);
    }
}
