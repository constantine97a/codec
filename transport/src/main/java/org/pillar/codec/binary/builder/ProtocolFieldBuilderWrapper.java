package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.convert.DefaultImplementationsMapper;
import org.pillar.codec.binary.core.Caching;
import org.pillar.codec.binary.core.Mapper;
import org.pillar.codec.binary.event.MessageHead;
import org.pillar.codec.binary.reflection.ReflectionProvider;
import org.pillar.codec.binary.reflection.SunUnsafeReflectionProvider;
import org.pillar.codec.binary.schema.CompositeField;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/18.
 * 准确的说应该是从目标类中直接获得Field的信息
 * 需要缓存CompositeField
 * 提供缓存的能力
 */
@Component
public class ProtocolFieldBuilderWrapper implements Caching {

    public static final int PRIORITY_VERY_HIGH = 10000;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_LOW = -10;
    public static final int PRIORITY_VERY_LOW = -20;

    /**
     * 只针对入协议进行缓存，对out不进行缓存
     */
    private transient ConcurrentMap<Class<?>, CompositeField> dictionaryEntries;


    /**
     * protocolFieldBuilder
     */
    private final ProtocolFieldBuilder protocolFieldBuilder;

    private final BuilderLookup builderLookup;

    private final Mapper defaultImplementMapper;

    private final ReflectionProvider reflectionProvider;

    /**
     * 协议生成器的Wrapper
     */
    public ProtocolFieldBuilderWrapper() {
        reflectionProvider = new SunUnsafeReflectionProvider();
        defaultImplementMapper = new DefaultImplementationsMapper();
        builderLookup = new DefaultBuilderLookup();
        BuilderRegistry builderRegistry = (BuilderRegistry) builderLookup;
        builderRegistry.registerBuilder(new IntFieldBuilder(), PRIORITY_NORMAL);
        builderRegistry.registerBuilder(new StringFieldBuilder(), PRIORITY_NORMAL);
        builderRegistry.registerBuilder(new DateFieldBuilder(), PRIORITY_NORMAL);
        builderRegistry.registerBuilder(new CollectionBuilder(defaultImplementMapper, reflectionProvider, builderLookup), PRIORITY_LOW);
        builderRegistry.registerBuilder(new ReflectionBuilder(reflectionProvider, defaultImplementMapper, builderLookup), PRIORITY_VERY_LOW);
        this.protocolFieldBuilder = new ProtocolFieldBuilder(builderLookup, reflectionProvider, defaultImplementMapper);
        dictionaryEntries = new ConcurrentHashMap<>();

        init();
    }

    /**
     * 初始化
     */
    protected void init() {
        addCache(MessageHead.class, protocolFieldBuilder.createMessageHeadCompositeField());
    }

    private <T> void addCache(Class<T> type, CompositeField compositeField) {
        checkNotNull(type);
        checkNotNull(compositeField);
        dictionaryEntries.putIfAbsent(type, compositeField);
    }


    /**
     * 查找Type对应的CompositeField
     *
     * @param type 类型信息
     * @return Composited
     */
    private <T> CompositeField findOrNull(Class<T> type) {
        checkNotNull(type);
        return dictionaryEntries.get(type);
    }


    /**
     * 找到类型对应的
     *
     * @param type
     * @return
     */
    public <T> CompositeField getInProtocolCompositedField(Class<T> type) {
        checkNotNull(type);
        CompositeField result = findOrNull(type);
        if (result == null) {
            result = this.protocolFieldBuilder.build((Object) null, type);
            checkNotNull(result);
            if (isCacheable(type))
                addCache(type, result);
        }
        return result;
    }

    /**
     * 判断该类型对应的Protocol是否能被缓存
     *
     * @param type type
     * @param <T>  Parameter Type
     * @return return Boolean.True if protocol can be cached
     */
    private <T> boolean isCacheable(Class<T> type) {
        Field[] fields = type.getDeclaredFields();
        boolean cacheable = true;
        for (Field field : fields) {
            if (!cacheable) {
                break;
            } else {
                Class<?> clazz = field.getType();
                cacheable = !Collection.class.isAssignableFrom(clazz);
            }
        }
        return cacheable;
    }

    /**
     * 动态获取协议
     *
     * @param value
     * @param type
     * @param <T>
     * @return
     */
    public <T> CompositeField getOutProtocolCompositedField(T value, Class<T> type) {
        return this.protocolFieldBuilder.build(value, type);
    }

    @Override
    public void flushCache() {
        checkNotNull(dictionaryEntries);
        dictionaryEntries.clear();
    }

    public ConcurrentMap<Class<?>, CompositeField> getDictionaryEntries() {
        return dictionaryEntries;
    }

    public ProtocolFieldBuilder getProtocolFieldBuilder() {
        return protocolFieldBuilder;
    }

    public BuilderLookup getBuilderLookup() {
        return builderLookup;
    }

    public Mapper getDefaultImplementMapper() {
        return defaultImplementMapper;
    }

    public ReflectionProvider getReflectionProvider() {
        return reflectionProvider;
    }
}
