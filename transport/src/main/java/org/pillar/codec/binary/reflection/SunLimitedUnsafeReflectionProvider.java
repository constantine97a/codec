package org.pillar.codec.binary.reflection;


import sun.misc.Unsafe;

import java.lang.reflect.Field;


public class SunLimitedUnsafeReflectionProvider extends PureJavaReflectionProvider {

    protected static final Unsafe unsafe;
    protected static final Exception exception;

    static {
        Unsafe u = null;
        Exception ex = null;
        try {
            final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            u = (Unsafe) unsafeField.get(null);
        } catch (final SecurityException | IllegalAccessException | NoSuchFieldException | IllegalArgumentException e) {
            ex = e;
        }
        exception = ex;
        unsafe = u;
    }

    /**
     * @since 1.4.7
     */
    public SunLimitedUnsafeReflectionProvider() {
        super();
    }

    /**
     * @since 1.4.7
     */
    public SunLimitedUnsafeReflectionProvider(final FieldDictionary fieldDictionary) {
        super(fieldDictionary);
    }

    public Object newInstance(final Class<?> type) {
        if (exception != null) {
            throw new RuntimeException("Cannot construct " + type.getName(), exception);
        }
        try {
            return unsafe.allocateInstance(type);
        } catch (final SecurityException | IllegalArgumentException | InstantiationException e) {
            throw new RuntimeException("Cannot construct " + type.getName(), e);
        }
    }


    @Override
    protected void validateFieldAccess(final Field field) {
        // (overriden) don't mind final fields.
    }

    private Object readResolve() {
        init();
        return this;
    }
}
