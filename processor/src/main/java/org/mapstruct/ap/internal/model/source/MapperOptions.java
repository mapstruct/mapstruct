/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.internal.util.ElementUtils;

import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MapperConfigGem;
import org.mapstruct.ap.internal.gem.MapperGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.gem.SubclassExhaustiveStrategyGem;

public class MapperOptions extends DelegatingOptions {

    private final MapperGem mapper;
    private final DeclaredType mapperConfigType;

    public static MapperOptions getInstanceOn(TypeElement typeElement, Options options) {
        MapperGem mapper = MapperGem.instanceOn( typeElement );
        MapperOptions mapperAnnotation;
        DelegatingOptions defaults = new DefaultOptions( mapper, options );
        DeclaredType mapperConfigType;
        if ( mapper.config().hasValue() && mapper.config().getValue().getKind() == TypeKind.DECLARED ) {
            mapperConfigType = (DeclaredType) mapper.config().get();
        }
        else {
            mapperConfigType = null;
        }
        if ( mapperConfigType != null ) {
            Element mapperConfigElement = mapperConfigType.asElement();
            MapperConfigGem mapperConfig = MapperConfigGem.instanceOn( mapperConfigElement );
            MapperConfigOptions mapperConfigAnnotation = new MapperConfigOptions( mapperConfig, defaults );
            mapperAnnotation = new MapperOptions( mapper, mapperConfigType, mapperConfigAnnotation );
        }
        else {
            mapperAnnotation = new MapperOptions( mapper, null, defaults );
        }
        return mapperAnnotation;
    }

    private MapperOptions(MapperGem mapper, DeclaredType mapperConfigType, DelegatingOptions next) {
        super( next );
        this.mapper = mapper;
        this.mapperConfigType = mapperConfigType;
    }

    @Override
    public String implementationName() {
        return mapper.implementationName().hasValue() ? mapper.implementationName().get() : next().implementationName();
    }

    @Override
    public String implementationPackage() {
        return mapper.implementationPackage().hasValue() ? mapper.implementationPackage().get() :
            next().implementationPackage();
    }

    @Override
    public Set<DeclaredType> uses() {
        return toDeclaredTypes( mapper.uses().get(), next().uses() );
    }

    @Override
    public Set<DeclaredType> imports() {
        return toDeclaredTypes( mapper.imports().get(), next().imports() );
    }

    @Override
    public ReportingPolicyGem unmappedTargetPolicy() {
        return mapper.unmappedTargetPolicy().hasValue() ?
            ReportingPolicyGem.valueOf( mapper.unmappedTargetPolicy().get() ) : next().unmappedTargetPolicy();
    }

    @Override
    public ReportingPolicyGem unmappedSourcePolicy() {
        return mapper.unmappedSourcePolicy().hasValue() ?
            ReportingPolicyGem.valueOf( mapper.unmappedSourcePolicy().get() ) : next().unmappedSourcePolicy();
    }

    @Override
    public ReportingPolicyGem typeConversionPolicy() {
        return mapper.typeConversionPolicy().hasValue() ?
            ReportingPolicyGem.valueOf( mapper.typeConversionPolicy().get() ) : next().typeConversionPolicy();
    }

    @Override
    public String componentModel() {
        return mapper.componentModel().hasValue() ? mapper.componentModel().get() : next().componentModel();
    }

    @Override
    public boolean suppressTimestampInGenerated() {
        return mapper.suppressTimestampInGenerated().hasValue() ?
            mapper.suppressTimestampInGenerated().get() :
            next().suppressTimestampInGenerated();
    }

    @Override
    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        return mapper.mappingInheritanceStrategy().hasValue() ?
            MappingInheritanceStrategyGem.valueOf( mapper.mappingInheritanceStrategy().get() ) :
            next().getMappingInheritanceStrategy();
    }

    @Override
    public InjectionStrategyGem getInjectionStrategy() {
        return mapper.injectionStrategy().hasValue() ?
            InjectionStrategyGem.valueOf( mapper.injectionStrategy().get() ) :
            next().getInjectionStrategy();
    }

    @Override
    public Boolean isDisableSubMappingMethodsGeneration() {
        return mapper.disableSubMappingMethodsGeneration().hasValue() ?
            mapper.disableSubMappingMethodsGeneration().get() :
            next().isDisableSubMappingMethodsGeneration();
    }

    // @Mapping, @BeanMapping

    @Override
    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        return mapper.collectionMappingStrategy().hasValue() ?
            CollectionMappingStrategyGem.valueOf( mapper.collectionMappingStrategy().get() ) :
            next().getCollectionMappingStrategy();
    }

    @Override
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return mapper.nullValueCheckStrategy().hasValue() ?
            NullValueCheckStrategyGem.valueOf( mapper.nullValueCheckStrategy().get() ) :
            next().getNullValueCheckStrategy();
    }

    @Override
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return mapper.nullValuePropertyMappingStrategy().hasValue() ?
            NullValuePropertyMappingStrategyGem.valueOf( mapper.nullValuePropertyMappingStrategy().get() ) :
            next().getNullValuePropertyMappingStrategy();
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return mapper.nullValueMappingStrategy().hasValue() ?
            NullValueMappingStrategyGem.valueOf( mapper.nullValueMappingStrategy().get() ) :
            next().getNullValueMappingStrategy();
    }

    @Override
    public SubclassExhaustiveStrategyGem getSubclassExhaustiveStrategy() {
        return mapper.subclassExhaustiveStrategy().hasValue() ?
            SubclassExhaustiveStrategyGem.valueOf( mapper.subclassExhaustiveStrategy().get() ) :
            next().getSubclassExhaustiveStrategy();
    }

    @Override
    public TypeMirror getSubclassExhaustiveException() {
        return mapper.subclassExhaustiveException().hasValue() ?
                mapper.subclassExhaustiveException().get() :
                next().getSubclassExhaustiveException();
    }

    @Override
    public NullValueMappingStrategyGem getNullValueIterableMappingStrategy() {
        if ( mapper.nullValueIterableMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapper.nullValueIterableMappingStrategy().get() );
        }
        if ( mapper.nullValueMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapper.nullValueMappingStrategy().get() );
        }
        return next().getNullValueIterableMappingStrategy();
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMapMappingStrategy() {
        if ( mapper.nullValueMapMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapper.nullValueMapMappingStrategy().get() );
        }
        if ( mapper.nullValueMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapper.nullValueMappingStrategy().get() );
        }
        return next().getNullValueMapMappingStrategy();
    }

    @Override
    public BuilderGem getBuilder() {
        return mapper.builder().hasValue() ? mapper.builder().get() : next().getBuilder();
    }

    @Override
    public MappingControl getMappingControl(ElementUtils elementUtils) {
        return mapper.mappingControl().hasValue() ?
            MappingControl.fromTypeMirror( mapper.mappingControl().getValue(), elementUtils ) :
            next().getMappingControl( elementUtils );
    }

    @Override
    public TypeMirror getUnexpectedValueMappingException() {
        return mapper.unexpectedValueMappingException().hasValue() ?
            mapper.unexpectedValueMappingException().get() :
            next().getUnexpectedValueMappingException();
    }

    // @Mapper specific

    public DeclaredType mapperConfigType() {
        return mapperConfigType;
    }

    public boolean hasMapperConfig() {
        return mapperConfigType != null;
    }

    public boolean isValid() {
        return mapper.isValid();
    }

    public AnnotationMirror getAnnotationMirror() {
        return mapper.mirror();
    }

    @Override
    public boolean hasAnnotation() {
        return true;
    }

}
