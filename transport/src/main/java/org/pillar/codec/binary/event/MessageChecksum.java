package org.pillar.codec.binary.event;

import org.apache.commons.codec.binary.Hex;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Created by pillar on 2015/8/13.
 */
public class MessageChecksum implements Serializable {
    private Integer checksum;

    @Override
    public String toString() {
        if (checksum != null) {
            return MessageFormat.format("checksum:{0}",
                    String.valueOf(Hex.encodeHex(new byte[]{(byte) checksum.intValue()})));
        } else {
            return "checksum{null}";
        }
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }
}
