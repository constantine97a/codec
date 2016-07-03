package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.annotation.PF;
import org.pillar.codec.binary.core.Mapper;
import org.pillar.codec.binary.reflection.ReflectionProvider;
import org.pillar.codec.binary.schema.CompositeField;
import org.pillar.codec.binary.schema.PField;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/25.
 */
public class ReflectionBuilder implements Builder<CompositeField> {


    private ReflectionProvider reflectionProvider;

    private Mapper implementationMapper;

    private BuilderLookup builderLookup;


    public ReflectionBuilder(ReflectionProvider reflectionProvider, Mapper implementationMapper, BuilderLookup builderLookup) {
        this.reflectionProvider = reflectionProvider;
        this.implementationMapper = implementationMapper;
        this.builderLookup = builderLookup;
    }

    /**
     * @param instance 用于创建协议的对象实例
     * @param type     对象的类型
     * @param name     Field Name
     * @param meta     Field的标注
     * @return CompositedField  协议的定义Field
     */
    @Override
    public CompositeField build(Object instance, Class<?> type, String name, PF meta) {
        checkNotNull(type);

        /**
         * if name is blank ,default using the class name as PField name
         */
        if (StringUtils.isBlank(name)) {
            name = StringUtils.uncapitalize(type.getSimpleName());
        }

        /**
         * if meta type is subclass of type declared
         */
        if (meta != null && meta.type() != null && type.isAssignableFrom(meta.type())) {
            type = meta.type();
        }

        /**
         *  find the implementationType ,if not in map ,just return self
         */
        final Class<?> implementationType = implementationMapper.defaultImplementationOf(type);

        if (instance == null) {
            instance = reflectionProvider.newInstance(implementationType);
        }

        final Object finInstance = instance;

        final CompositeField compositeField = new CompositeField(name, type);

        reflectionProvider.visitSerializableFields(instance, new ReflectionProvider.Visitor() {
            @Override
            public void visit(String name, Class<?> type, Class<?> definedIn, Object value) {
                Field field = reflectionProvider.getField(implementationType, name);
                PF f = field.getAnnotation(PF.class);
                if (f != null) {
                    try {
                        Builder<PField> builder = builderLookup.lookupBuilderForType(type);
                        PField pField = builder.build(field.get(finInstance), reflectionProvider.getFieldType(finInstance, name, definedIn), name, f);
                        compositeField.addField(pField);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return compositeField;
    }

    /**
     * @param type Field的类型
     * @return
     */
    @Override
    public boolean isCompatible(Class<?> type) {
        return true;
    }
}
