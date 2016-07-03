package org.pillar.codec.binary.event;

import java.io.Serializable;

/**
 * Created by pillar on 2015/8/13.
 */
public class MessageHead implements Serializable {

    private String start;

    private int length;

    private int version;

    private int sequence;

    private int command;


    /**
     * default construct
     */
    public MessageHead() {
    }

    public MessageHead(String start, int length, int version, int sequence, int command) {
        this.start = start;
        this.length = length;
        this.version = version;
        this.sequence = sequence;
        this.command = command;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "MessageHead{" +
                "start='" + start + '\'' +
                ", length=" + length +
                ", version=" + version +
                ", sequence=" + sequence +
                ", command=" + command +
                '}';
    }
}
