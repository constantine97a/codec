package org.pillar.codec.binary.core;

import org.pillar.codec.binary.event.Value;

import java.util.List;

/**
 * Created by pillar on 2015/8/18.
 */
public class TestMessageBody {
    private int value;
    private String stringValue;
    private List<Value> values;

    private List<Value> values2;

    public List<Value> getValues2() {
        return values2;
    }

    public void setValues2(List<Value> values2) {
        this.values2 = values2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "TestMessageBody{" +
                "value=" + value +
                ", stringValue='" + stringValue + '\'' +
                ", values=" + values +
                ", values2=" + values2 +
                '}';
    }
}
