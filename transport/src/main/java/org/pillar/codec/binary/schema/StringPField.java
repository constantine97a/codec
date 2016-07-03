package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.StringCodec;

import java.text.MessageFormat;

/**
 * Created by pillar on 2015/8/15.
 */
public class StringPField extends PField {
    public StringPField(String name, int length) {
        super(name, String.class, new StringCodec(length), length);
    }


    @Override
    public void padding(int left) {
        if ("password".equals(this.getName())) {
            //设置field 长度
            int length = this.getLength() + (left - 1);
            this.setLength(length);
            //设置解析器的长度
            ((StringCodec) this.getCodec()).setLength(length);
        }
    }


    @Override
    public String toString() {
        return MessageFormat.format("{0}[chars,{1} byte]", getName(), getLength());
    }
}
