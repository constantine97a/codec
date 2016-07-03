package org.pillar.codec.binary.event;

import java.io.Serializable;

/**
 * Created by pillar on 2015/8/13.
 */
public abstract class MessageBody implements Serializable {
    public abstract String getChannelKey();
}
