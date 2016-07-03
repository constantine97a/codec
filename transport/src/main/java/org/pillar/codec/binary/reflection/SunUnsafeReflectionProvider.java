package org.pillar.codec.binary.reflection;


import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class SunUnsafeReflectionProvider extends SunLimitedUnsafeReflectionProvider {

	// references to the PField key are kept in the FieldDictionary
	private transient ConcurrentMap<Field, Long> fieldOffsetCache;

	/**
	 * @since 1.4.7
	 */
	public SunUnsafeReflectionProvider() {
		super();
	}

	/**
	 * @since 1.4.7
	 */
	public SunUnsafeReflectionProvider(final FieldDictionary dic) {
		super(dic);
	}

	@Override
	public void writeField(final Object object, final String fieldName, final Object value, final Class<?> definedIn) {
		write(fieldDictionary.field(object.getClass(), fieldName, definedIn), object, value);
	}

	public Object readField(final Object object, String fieldName, final Class<?> definedIn) {
		return read(fieldDictionary.field(object.getClass(), fieldName, definedIn), object);
	}

	private void write(final Field field, final Object object, final Object value) {
		if (exception != null) {
			throw new RuntimeException("Could not set field " + object.getClass() + "." + field.getName(),
					exception);
		}
		try {
			final long offset = getFieldOffset(field);
			final Class<?> type = field.getType();
			if (type.isPrimitive()) {
				if (type.equals(Integer.TYPE)) {
					unsafe.putInt(object, offset, ((Integer) value).intValue());
				} else if (type.equals(Long.TYPE)) {
					unsafe.putLong(object, offset, ((Long) value).longValue());
				} else if (type.equals(Short.TYPE)) {
					unsafe.putShort(object, offset, ((Short) value).shortValue());
				} else if (type.equals(Character.TYPE)) {
					unsafe.putChar(object, offset, ((Character) value).charValue());
				} else if (type.equals(Byte.TYPE)) {
					unsafe.putByte(object, offset, ((Byte) value).byteValue());
				} else if (type.equals(Float.TYPE)) {
					unsafe.putFloat(object, offset, ((Float) value).floatValue());
				} else if (type.equals(Double.TYPE)) {
					unsafe.putDouble(object, offset, ((Double) value).doubleValue());
				} else if (type.equals(Boolean.TYPE)) {
					unsafe.putBoolean(object, offset, ((Boolean) value).booleanValue());
				} else {
					throw new RuntimeException("Could not set field "
							+ object.getClass()
							+ "."
							+ field.getName()
							+ ": Unknown type "
							+ type);
				}
			} else {
				unsafe.putObject(object, offset, value);
			}

		} catch (final IllegalArgumentException e) {
			throw new RuntimeException("Could not set field " + object.getClass() + "." + field.getName(), e);
		}
	}

	private Object read(final Field field, final Object object) {
		if (exception != null) {
			throw new RuntimeException("Could not set field " + object.getClass() + "." + field.getName(),
					exception);
		}
		try {
			final long offset = getFieldOffset(field);
			final Class<?> type = field.getType();
			if (type.isPrimitive()) {
				if (type.equals(Integer.TYPE)) {
					return unsafe.getInt(object, offset);
				} else if (type.equals(Long.TYPE)) {
					return unsafe.getLong(object, offset);
				} else if (type.equals(Short.TYPE)) {
					return unsafe.getShort(object, offset);
				} else if (type.equals(Character.TYPE)) {
					return unsafe.getChar(object, offset);
				} else if (type.equals(Byte.TYPE)) {
					return unsafe.getByte(object, offset);
				} else if (type.equals(Float.TYPE)) {
					return unsafe.getFloat(object, offset);
				} else if (type.equals(Double.TYPE)) {
					return unsafe.getDouble(object, offset);
				} else if (type.equals(Boolean.TYPE)) {
					return unsafe.getBoolean(object, offset);
				} else {
					throw new RuntimeException("Could not set field "
							+ object.getClass()
							+ "."
							+ field.getName()
							+ ": Unknown type "
							+ type);
				}
			} else {
				return unsafe.getObject(object, offset);
			}

		} catch (final IllegalArgumentException e) {
			throw new RuntimeException("Could not set field " + object.getClass() + "." + field.getName(), e);
		}
	}

	private long getFieldOffset(final Field f) {
		Long l = fieldOffsetCache.get(f);
		if (l == null) {
			fieldOffsetCache.putIfAbsent(f, Long.valueOf(unsafe.objectFieldOffset(f)));
			l = fieldOffsetCache.get(f);
		}

		return l.longValue();
	}

	private Object readResolve() {
		init();
		return this;
	}

	@Override
	protected void init() {
		super.init();
		fieldOffsetCache = new ConcurrentHashMap<>();
	}
}
