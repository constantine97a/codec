package org.pillar.codec.binary.convert;

import org.pillar.codec.binary.codec.Codec;
import org.pillar.codec.binary.core.*;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/19.
 */
public class DateConverter implements Converter {
    @Override
    public void marshal(Object source, StreamWriter writer, MarshallingContext context) {
        Codec codec = writer.getCurrentCodec();
        writer.writeValue(codec.encode(source));
    }

    @Override
    public Object unmarshal(StreamReader reader, UnmarshallingContext context) {
        Codec codec = reader.getCurrentCodec();
        return codec.decode(reader.readValue());
    }

    @Override
    public boolean canConvert(Class<?> type) {
        checkNotNull(type);
        return type.equals(Date.class);
    }
}
