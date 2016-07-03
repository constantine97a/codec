package org.pillar.codec.binary.core;

import java.util.Arrays;

/**
 * Created by pillar on 2015/8/17.
 */
public class Stack<T> {
    private T[] stack;
    private int pointer;

    public Stack(final int initialCapacity) {
        @SuppressWarnings("unchecked")
        final T[] array = getArray(initialCapacity);
        stack = array;
    }

    private T[] getArray(final int capacity, final T... t) {
        return Arrays.copyOf(t, capacity);
    }

    public T push(final T value) {
        if (pointer + 1 >= stack.length) {
            resizeStack(stack.length * 2);
        }
        stack[pointer++] = value;
        return value;
    }

    public void popSilently() {
        stack[--pointer] = null;
    }

    public T pop() {
        final T result = stack[--pointer];
        stack[pointer] = null;
        return result;
    }

    public T peek() {
        return pointer == 0 ? null : stack[pointer - 1];
    }

    public Object replace(final T value) {
        final T result = stack[pointer - 1];
        stack[pointer - 1] = value;
        return result;
    }

    public void replaceSilently(final T value) {
        stack[pointer - 1] = value;
    }

    public int size() {
        return pointer;
    }

    public boolean hasStuff() {
        return pointer > 0;
    }

    public T get(final int i) {
        return stack[i];
    }

    private void resizeStack(final int newCapacity) {
        @SuppressWarnings("unchecked")
        final T[] newStack = getArray(newCapacity);
        System.arraycopy(stack, 0, newStack, 0, Math.min(pointer, newCapacity));
        stack = newStack;
    }

    @Override
    public String toString() {
        final StringBuffer result = new StringBuffer("[");
        for (int i = 0; i < pointer; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(stack[i]);
        }
        result.append(']');
        return result.toString();
    }
}
