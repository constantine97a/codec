package org.pillar.codec.binary.reflection;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by pillar on 2015/8/18.
 */
public class PrioritizedList<E> implements Iterable<E> {

    private final Set<PrioritizedItem<E>> set = new TreeSet<PrioritizedItem<E>>();
    private int lowestPriority = Integer.MAX_VALUE;
    private int lastId = 0;

    public void add(final E item, final int priority) {
        if (this.lowestPriority > priority) {
            this.lowestPriority = priority;
        }
        this.set.add(new PrioritizedItem<E>(item, priority, ++lastId));
    }

    @Override
    public Iterator<E> iterator() {
        return new PrioritizedItemIterator<E>(this.set.iterator());
    }

    private static class PrioritizedItem<V> implements Comparable<PrioritizedItem<V>> {

        final V value;
        final int priority;
        final int id;

        public PrioritizedItem(final V value, final int priority, final int id) {
            this.value = value;
            this.priority = priority;
            this.id = id;
        }

        @Override
        public int compareTo(final PrioritizedItem<V> other) {
            if (this.priority != other.priority) {
                return other.priority - this.priority;
            }
            return other.id - this.id;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(id).hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof PrioritizedItem)) {
                return false;
            }
            @SuppressWarnings("unchecked")
            final PrioritizedItem<V> other = (PrioritizedItem<V>) obj;
            return this.id == other.id;
        }

    }

    private static class PrioritizedItemIterator<V> implements Iterator<V> {

        private final Iterator<PrioritizedItem<V>> iterator;

        public PrioritizedItemIterator(final Iterator<PrioritizedItem<V>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public V next() {
            return iterator.next().value;
        }

    }

}
