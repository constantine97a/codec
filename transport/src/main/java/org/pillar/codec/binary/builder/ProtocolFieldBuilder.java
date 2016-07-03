package org.pillar.codec.binary.builder;

import org.apache.commons.lang3.StringUtils;
import org.pillar.codec.binary.annotation.PF;
import org.pillar.codec.binary.core.Mapper;
import org.pillar.codec.binary.event.MessageHead;
import org.pillar.codec.binary.reflection.ReflectionProvider;
import org.pillar.codec.binary.schema.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by pillar on 2015/8/20.
 * ProtocalFieldBuilder
 */
public class ProtocolFieldBuilder {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(ProtocolFieldBuilder.class);

    private BuilderLookup builderLookup;


    private ReflectionProvider reflectionProvider;
    private Mapper implementationMapper;

    public ProtocolFieldBuilder(BuilderLookup builderLookup,
                                ReflectionProvider reflectionProvider,
                                Mapper implementationMapper) {
        this.builderLookup = builderLookup;
        this.reflectionProvider = reflectionProvider;
        this.implementationMapper = implementationMapper;
    }


    /**
     * @return
     */
    public CompositeField createMessageHeadCompositeField() {
        CompositeField header;
        header = new CompositeField("head", MessageHead.class);
        header.addField(new StartPField("start"));
        header.addField(new LengthPField("length"));
        header.addField(new VersionPField("version"));
        header.addField(new SequencePField("sequence"));
        header.addField(new CommandPField("command"));
        return header;
    }


    /**
     * 找到相应的Builder进行调用Build
     *
     * @param value 类型值
     * @param type  类型Class
     * @return 描述对象CompositeField
     */
    public CompositeField build(Object value, Class<?> type) {
        checkNotNull(type);
        Class<?> clazz = this.implementationMapper.defaultImplementationOf(type);
        if (value == null) {
            value = reflectionProvider.newInstance(clazz);
        }
        Builder<CompositeField> builder = builderLookup.lookupBuilderForType(clazz);
        return builder.build(value, clazz, StringUtils.uncapitalize(clazz.getSimpleName()), (PF) null);
    }


    public Logger getLogger() {
        return logger;
    }

    public BuilderLookup getBuilderLookup() {
        return builderLookup;
    }

    public ReflectionProvider getReflectionProvider() {
        return reflectionProvider;
    }

    public Mapper getImplementationMapper() {
        return implementationMapper;
    }


}
