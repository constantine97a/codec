package org.pillar.codec.binary.convert;

import org.pillar.codec.binary.core.Mapper;

import java.util.*;

/**
 * Created by pillar on 2015/8/18.
 */
public class DefaultImplementationsMapper implements Mapper {

    private final Map<Class<?>, Class<?>> typeToImpl = new HashMap<Class<?>, Class<?>>();
    private transient Map<Class<?>, Class<?>> implToType = new HashMap<Class<?>, Class<?>>();

    public DefaultImplementationsMapper() {
        addDefaults();
    }

    protected void addDefaults() {
        // null handling
        addDefaultImplementation(null, Mapper.Null.class);
        // register primitive types
        addDefaultImplementation(Boolean.class, boolean.class);
        addDefaultImplementation(Character.class, char.class);
        addDefaultImplementation(Integer.class, int.class);
        addDefaultImplementation(Float.class, float.class);
        addDefaultImplementation(Double.class, double.class);
        addDefaultImplementation(Short.class, short.class);
        addDefaultImplementation(Byte.class, byte.class);
        addDefaultImplementation(Long.class, long.class);


        addDefaultImplementation(HashMap.class, Map.class);
        addDefaultImplementation(ArrayList.class, List.class);
        addDefaultImplementation(HashSet.class, Set.class);
        addDefaultImplementation(TreeSet.class, SortedSet.class);
        addDefaultImplementation(GregorianCalendar.class, Calendar.class);
    }

    public void addDefaultImplementation(final Class<?> defaultImplementation, final Class<?> ofType) {
        if (defaultImplementation != null && defaultImplementation.isInterface()) {
            throw new IllegalArgumentException("Default implementation is not a concrete class: "
                    + defaultImplementation.getName());
        }
        typeToImpl.put(ofType, defaultImplementation);
        implToType.put(defaultImplementation, ofType);
    }


    @Override
    public Class<?> defaultImplementationOf(final Class<?> type) {
        if (typeToImpl.containsKey(type)) {
            return typeToImpl.get(type);
        } else {
            return type;
        }

    }
}
