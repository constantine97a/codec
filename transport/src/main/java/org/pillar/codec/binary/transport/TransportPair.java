package org.pillar.codec.binary.transport;

/**
 * Created by pillar on 2015/8/14.
 */
public class TransportPair {
    private final Transport inputTransport;
    private final Transport outputTransport;

    protected TransportPair(Transport inputTransport, Transport outputTransport) {
        this.inputTransport = inputTransport;
        this.outputTransport = outputTransport;
    }

    public Transport getInputTransport() {
        return inputTransport;
    }

    public Transport getOutputTransport() {
        return outputTransport;
    }

    public static TransportPair fromSeparateTransports(final Transport inputTransport,
                                                   final Transport outputTransport) {
        return new TransportPair(inputTransport, outputTransport);
    }

    public static TransportPair fromSingleTransport(final Transport transport) {
        return new TransportPair(transport, transport);
    }
}
