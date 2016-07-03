
package org.pillar.codec.binary.reflection;

import com.thoughtworks.xstream.converters.reflection.FieldKey;
import com.thoughtworks.xstream.converters.reflection.FieldKeySorter;
import com.thoughtworks.xstream.converters.reflection.ImmutableFieldKeySorter;
import com.thoughtworks.xstream.core.Caching;
import com.thoughtworks.xstream.core.JVM;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class FieldDictionary implements Caching {

    private static final DictionaryEntry OBJECT_DICTIONARY_ENTRY = new DictionaryEntry(Collections
            .<String, Field>emptyMap(), Collections.<FieldKey, Field>emptyMap());
    private final FieldKeySorter sorter;
    private transient ConcurrentMap<Class<?>, DictionaryEntry> dictionaryEntries;

    public FieldDictionary() {
        this(new ImmutableFieldKeySorter());
    }

    public FieldDictionary(final FieldKeySorter sorter) {
        this.sorter = sorter;
        init();
    }

    private void init() {
        dictionaryEntries = new ConcurrentHashMap<Class<?>, DictionaryEntry>();
    }

    public Iterator<Field> fieldsFor(final Class<?> cls) {
        return buildMap(cls, true).values().iterator();
    }


    public Field field(final Class<?> cls, final String name, final Class<?> definedIn) {
        final Field field = fieldOrNull(cls, name, definedIn);
        if (field == null) {
            throw new RuntimeException(cls.getName() + "|name" + name);
        } else {
            return field;
        }
    }


    public Field fieldOrNull(final Class<?> cls, final String name, final Class<?> definedIn) {
        final Map<?, Field> fields = buildMap(cls, definedIn != null);
        return fields.get(definedIn != null ? new FieldKey(name, definedIn, -1) : name);
    }

    private Map<?, Field> buildMap(final Class<?> type, final boolean tupleKeyed) {
        DictionaryEntry dictionaryEntry = dictionaryEntries.get(type);
        if (dictionaryEntry == null) {
            dictionaryEntry = buildCache(type);
        }
        return tupleKeyed ? dictionaryEntry.getKeyedByFieldKey() : dictionaryEntry.getKeyedByFieldName();
    }

    private DictionaryEntry buildCache(final Class<?> type) {
        Class<?> cls = type;
        DictionaryEntry lastDictionaryEntry = null;
        final LinkedList<Class<?>> superClasses = new LinkedList<Class<?>>();
        while (lastDictionaryEntry == null) {
            if (Object.class.equals(cls) || cls == null) {
                lastDictionaryEntry = OBJECT_DICTIONARY_ENTRY;
            } else {
                lastDictionaryEntry = dictionaryEntries.get(cls);
            }
            if (lastDictionaryEntry == null) {
                superClasses.addFirst(cls);
                cls = cls.getSuperclass();
            }
        }
        for (final Class<?> element : superClasses) {
            cls = element;
            DictionaryEntry currentDictionaryEntry = dictionaryEntries.get(cls);
            if (currentDictionaryEntry == null) {
                currentDictionaryEntry = buildDictionaryEntryForClass(cls, lastDictionaryEntry);
                final DictionaryEntry existingValue = dictionaryEntries.putIfAbsent(cls, currentDictionaryEntry);
                if (existingValue != null) {
                    currentDictionaryEntry = existingValue;
                }
            }
            lastDictionaryEntry = currentDictionaryEntry;
        }
        return lastDictionaryEntry;
    }

    @SuppressWarnings("deprecation")
    private DictionaryEntry buildDictionaryEntryForClass(final Class<?> cls, final DictionaryEntry lastDictionaryEntry) {
        final Map<String, Field> keyedByFieldName =
                new HashMap<>(lastDictionaryEntry.getKeyedByFieldName());
        final Map<FieldKey, Field> keyedByFieldKey =
                new LinkedHashMap<>(lastDictionaryEntry.getKeyedByFieldKey());
        final Field[] fields = cls.getDeclaredFields();
        if (JVM.reverseFieldDefinition()) {
            reverseFieldsArray(fields);
        }
        for (int i = 0; i < fields.length; i++) {
            final Field field = fields[i];
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            final FieldKey fieldKey = new FieldKey(field.getName(), field.getDeclaringClass(), i);
            final Field existent = keyedByFieldName.get(field.getName());
            if (existent == null
                    // do overwrite statics
                    || (existent.getModifiers() & Modifier.STATIC) != 0
                    // overwrite non-statics with non-statics only
                    || (existent != null && (field.getModifiers() & Modifier.STATIC) == 0)) {
                keyedByFieldName.put(field.getName(), field);
            }
            keyedByFieldKey.put(fieldKey, field);
        }
        final Map<FieldKey, Field> sortedFieldKeys = sorter.sort(cls, keyedByFieldKey);
        return new DictionaryEntry(keyedByFieldName, sortedFieldKeys);
    }

    private void reverseFieldsArray(final Field[] fields) {
        for (int i = fields.length >> 1; i-- > 0; ) {
            final int idx = fields.length - i - 1;
            final Field field = fields[i];
            fields[i] = fields[idx];
            fields[idx] = field;
        }
    }

    @Override
    public void flushCache() {
        if (sorter instanceof Caching) {
            ((Caching) sorter).flushCache();
        }
        dictionaryEntries.clear();
    }

    protected Object readResolve() {
        init();
        return this;
    }

    private static final class DictionaryEntry {

        private final Map<String, Field> keyedByFieldName;
        private final Map<FieldKey, Field> keyedByFieldKey;

        public DictionaryEntry(final Map<String, Field> keyedByFieldName, final Map<FieldKey, Field> keyedByFieldKey) {
            super();
            this.keyedByFieldName = keyedByFieldName;
            this.keyedByFieldKey = keyedByFieldKey;
        }

        public Map<String, Field> getKeyedByFieldName() {
            return keyedByFieldName;
        }

        public Map<FieldKey, Field> getKeyedByFieldKey() {
            return keyedByFieldKey;
        }

    }

}
