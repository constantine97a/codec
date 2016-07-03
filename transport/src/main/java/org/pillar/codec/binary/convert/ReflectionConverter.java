package org.pillar.codec.binary.convert;

import org.pillar.codec.binary.reflection.ReflectionProvider;
import org.pillar.codec.binary.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by pillar on 2015/8/18.
 * ReflectionConverter
 */
public class ReflectionConverter implements Converter {

	private final Logger logger = LoggerFactory.getLogger(ReflectionConverter.class);

	private Mapper mapper;

	private ReflectionProvider reflectionProvider;

	public ReflectionConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
		this.mapper = mapper;
		this.reflectionProvider = reflectionProvider;
	}


	/**
	 * 编码
	 *
	 * @param source  source
	 * @param writer  writer
	 * @param context context
	 */
	@Override
	public void marshal(Object source, StreamWriter writer, MarshallingContext context) {
		doMarshal(source, writer, context);
	}

	protected void doMarshal(final Object source, final StreamWriter writer, final MarshallingContext context) {
		Class<?> parentClazz = context.getRequiredType();
		while (writer.hasMoreChildren()) {
			writer.moveDown();
			String childFieldName = writer.getCurrentNodeName();
			Field childField = reflectionProvider.getField(parentClazz, childFieldName);
			Class<?> fieldType = mapper.defaultImplementationOf(childField.getType());
			final Object fieldValue = reflectionProvider.readField(source, childFieldName, parentClazz);
			marshallField(context, fieldValue, fieldType, childField);
			writer.moveUp();
		}
	}

	@Override
	public Object unmarshal(StreamReader reader, UnmarshallingContext context) {
		Object result = instantiateNewInstance(context);
		result = doUnmarshal(result, reader, context);
		return result;
	}

	public Object doUnmarshal(final Object result, final StreamReader reader,
	                          final UnmarshallingContext context) {
		Class parentClazz = context.getRequiredType();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String childFieldName = reader.getCurrentNodeName();
			Field childField = reflectionProvider.getField(parentClazz, childFieldName);
			Class<?> fieldType = mapper.defaultImplementationOf(childField.getType());
			Object fieldValue = unmarshallField(context, result, fieldType, childField);
			reflectionProvider.writeField(result, childFieldName, fieldValue, childField.getDeclaringClass());
			reader.moveUp();
		}
		return result;
	}

	protected Object unmarshallField(final UnmarshallingContext context, final Object result, final Class<?> type,
	                                 final Field field) {
		return context.convertAnother(result, type, (Converter) null);

	}

	protected void marshallField(final MarshallingContext context, final Object source, final Class<?> type,
	                             final Field field) {
		context.convertAnother(source, type, (Converter) null);
	}


	@Override
	public boolean canConvert(final Class<?> type) {
		return canAccess(type);
	}


	protected boolean canAccess(final Class<?> type) {
		try {
			reflectionProvider.getFieldOrNull(type, "%");
			return true;
		} catch (final NoClassDefFoundError e) {
			// restricted type in GAE
		}
		return false;
	}

	protected Object instantiateNewInstance(final UnmarshallingContext context) {
		return reflectionProvider.newInstance(context.getRequiredType());
	}
}
