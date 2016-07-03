package org.pillar.codec.binary.core;

import org.pillar.codec.binary.exception.ConversionException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/17.
 * 对协议进行分析对Event进行解析序列化
 */
public class Marshaller implements MarshallingContext {

	private final Logger logger = LoggerFactory.getLogger(Marshaller.class);

	private final Stack<Class<?>> types = new Stack<>(1024);

	private StreamWriter writer;

	private Class<?> rootClazz;

	private ConverterLookup converterLookup;

	private Mapper mapper;

	private Object root;


	public Marshaller(Object root, StreamWriter writer, Class<?> clazz, ConverterLookup converterLookup, Mapper mapper) {
		checkNotNull(writer);
		checkNotNull(clazz);
		this.root = root;
		this.writer = writer;
		this.rootClazz = clazz;
		this.converterLookup = converterLookup;
		this.mapper = mapper;
	}

	public void start() {
		Class type = rootClazz;
		convertAnother(root, type);
	}


	@Override
	public void convertAnother(Object object, Class<?> type) {
		convertAnother(object, type, null);
	}

	@Override
	public void convertAnother(Object object, Class<?> type, Converter converter) {
		logger.debug("MARSHALLING OBJECT:{},TYPE:{},CONVERTER:{}", object, type, converter);
		type = mapper.defaultImplementationOf(type);
		if (converter == null) {
			converter = converterLookup.lookupConverterForType(type);
		}
		logger.debug("MARSHALLING OBJECT:{},TYPE:{},CONVERTER:{}", object, type, converter);
		convert(object, type, converter);
	}

	/**
	 * execute 方法真正执行转换的方法
	 *
	 * @param object    waiting marshalling object
	 * @param type      type for marshalling object
	 * @param converter converter for marshalling object
	 */
	protected void convert(Object object, Class<?> type, Converter converter) {
		checkNotNull(object, "MARSHALLING OBJECT IS NULL!:" + object);
		checkNotNull(type, "TYPE OF MARSHALLING OBJECT IS NULl!:" + type.getSimpleName());
		checkNotNull(converter, "CONVERTER IS NULL");
		try {
			types.push(type);
			converter.marshal(object, writer, this);
			types.popSilently();
		} catch (final Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new ConversionException(e);
		}
	}

	@Override
	public Class<?> getRequiredType() {
		return types.peek();
	}
}
