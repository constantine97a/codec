package org.pillar.codec.binary.convert;

import org.pillar.codec.binary.core.*;

import java.util.*;

/**
 * Created by pillar on 2015/8/18.
 */
public class CollectionConverter extends AbstractCollectionConverter {


    private final Class<? extends Collection<?>> type;

    public CollectionConverter(final Mapper mapper) {
        this(mapper, null);
    }

    public CollectionConverter(final Mapper mapper, @SuppressWarnings("rawtypes") final Class<? extends Collection> type) {
        super(mapper);
        @SuppressWarnings("unchecked")
        final Class<? extends Collection<?>> checkedType = (Class<? extends Collection<?>>)type;
        this.type = checkedType;
        if (type != null && !Collection.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException(type + " not of type " + Collection.class);
        }
    }


    @Override
    public boolean canConvert(final Class<?> type) {
        if (this.type != null) {
            return type.equals(this.type);
        }
        return type.equals(ArrayList.class)
                || type.equals(HashSet.class)
                || type.equals(LinkedList.class)
                || type.equals(Vector.class)
                || type.equals(LinkedHashSet.class);
    }



    @Override
    public void marshal(final Object source, final StreamWriter writer, final MarshallingContext context) {
        final Collection<?> collection = (Collection<?>)source;
        for (final Object item : collection) {
            writer.moveDown();
            writeItem(item, context, writer);
            writer.moveUp();
        }
    }

    @Override
    public Object unmarshal(final StreamReader reader, final UnmarshallingContext context) {
        final Class<?> collectionType = context.getRequiredType();
        final Collection<?> collection = createCollection(collectionType);
        populateCollection(reader, context, collection);
        return collection;
    }

    protected void populateCollection(final StreamReader reader, final UnmarshallingContext context,
                                      final Collection<?> collection) {
        populateCollection(reader, context, collection, collection);
    }

    protected void populateCollection(final StreamReader reader, final UnmarshallingContext context,
                                      final Collection<?> collection, final Collection<?> target) {
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            addCurrentElementToCollection(reader, context, collection, target);
            reader.moveUp();
        }
    }

    protected void addCurrentElementToCollection(final StreamReader reader,
                                                 final UnmarshallingContext context, final Collection<?> collection, final Collection<?> target) {
        final Object item = readItem(reader, context, collection);
        final Collection<Object> targetCollection = (Collection<Object>)target;
        targetCollection.add(item);
    }

    @Override
    protected Collection<?> createCollection(final Class<?> type) {
        return (Collection<?>)super.createCollection(this.type != null ? this.type : type);
    }
}
