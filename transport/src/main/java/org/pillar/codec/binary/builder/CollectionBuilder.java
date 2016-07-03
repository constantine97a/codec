package org.pillar.codec.binary.builder;

import org.pillar.codec.binary.annotation.PF;
import org.pillar.codec.binary.core.Mapper;
import org.pillar.codec.binary.reflection.ReflectionProvider;
import org.pillar.codec.binary.schema.PField;
import org.pillar.codec.binary.schema.CollectionPField;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by pillar on 2015/8/25.
 */
public class CollectionBuilder implements Builder<CollectionPField> {


    private Mapper defaultImplementationMapper;

    private ReflectionProvider reflectionProvider;

    private BuilderLookup builderLookup;

    public CollectionBuilder(Mapper defaultImplementationMapper, ReflectionProvider reflectionProvider, BuilderLookup builderLookup) {
        this.defaultImplementationMapper = defaultImplementationMapper;
        this.reflectionProvider = reflectionProvider;
        this.builderLookup = builderLookup;
    }

    /**
     * build
     *
     * @param source
     * @param type
     * @param name
     * @param meta
     * @return
     */
    @Override
    public CollectionPField build(Object source, Class<?> type, String name, PF meta) {
        if (StringUtils.isBlank(name)) {
            name = StringUtils.uncapitalize(type.getSimpleName());
        }
        Collection collection = (Collection) source;
        if (collection == null) {
            type = defaultImplementationMapper.defaultImplementationOf(type);
            collection = (Collection) reflectionProvider.newInstance(type);
        }
        PField prototype = buildItem(null, meta.item());
        CollectionPField collectionField = new CollectionPField(name, prototype, type);
        for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            collectionField.addField(buildItem(iterator.next(), meta.item()));
        }
        return collectionField;
    }

    protected PField buildItem(Object itemValue, Class<?> itemClazz) {
        itemClazz = defaultImplementationMapper.defaultImplementationOf(itemClazz);
        Builder<PField> builder = builderLookup.lookupBuilderForType(itemClazz);
        PF f = itemClazz.getAnnotation(PF.class);
        return builder.build(itemValue, itemClazz, StringUtils.EMPTY, f);
    }


    @Override
    public boolean isCompatible(Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
