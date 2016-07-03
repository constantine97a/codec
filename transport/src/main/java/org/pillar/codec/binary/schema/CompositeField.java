package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.DummyCodec;
import com.google.common.collect.Lists;

import java.text.MessageFormat;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/12.
 * 提供组合的方式，提供迭代器，让外部的调用者能够调用
 * 定长的
 * Composited 对应的是类或是列表
 */
public class CompositeField extends PField implements Iterable<PField> {
    /**
     * 初始化长度
     */
    public static final int INITIAL_LENGTH = 0;

    private List<PField> pfields = Lists.newArrayList();

    public CompositeField(String name, Class<?> clazz) {
        super(name, clazz, new DummyCodec(), INITIAL_LENGTH);
    }

    /**
     * @param field add field
     */
    public void addField(PField field) {
        checkNotNull(field);
        if (this == field) {
            throw new IllegalArgumentException("cycle reference is detected!");
        }
        field.setParent(this);
        this.pfields.add(field);
    }

    public void addField(PField... fields) {
        for (PField component : fields) {
            addField(component);
        }
    }

    public int getLength() {
        int length = 0;
        for (PField component : pfields) {
            length += component.getLength();
        }
        setLength(length);
        return length;
    }


    @Override
    public List<PField> getChildren() {
        return this.pfields;
    }

    @Override
    public int getChildCount() {
        return getChildren().size();
    }

    //<editor-fold desc="iterator">
    @Override
    public Iterator<PField> iterator() {
        return new Itr();
    }

    @Override
    public boolean hasChild() {
        if (this.pfields != null && this.pfields.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public PField getChild(int index) {
        return this.pfields.get(index);
    }

    //</editor-fold>

    public PField getLast() {
        return this.pfields.get(pfields.size() - 1);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[\n");
        stringBuilder.append(MessageFormat.format("name:{0},len:{1}", getName(), getLength()));
        for (PField item : this.pfields) {
            stringBuilder.append("\n");
            stringBuilder.append(item);
        }
        stringBuilder.append("\n]");
        return stringBuilder.toString();

    }

    private final class Itr implements Iterator<PField> {
        private final int size = pfields.size();

        private int index;

        @Override
        public boolean hasNext() {
            return size > index;
        }

        @Override
        public PField next() {
            if (size != pfields.size()) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            try {
                return pfields.get(index++);
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Read-Only");
        }
    }

}
