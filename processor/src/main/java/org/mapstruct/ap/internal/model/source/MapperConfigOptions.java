/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Set;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.internal.util.ElementUtils;

import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MapperConfigGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.gem.SubclassExhaustiveStrategyGem;

public class MapperConfigOptions extends DelegatingOptions {

    private final MapperConfigGem mapperConfig;

    MapperConfigOptions(MapperConfigGem mapperConfig, DelegatingOptions next ) {
        super( next );
        this.mapperConfig = mapperConfig;
    }

    @Override
    public String implementationName() {
        return mapperConfig.implementationName().hasValue() ? mapperConfig.implementationName().get() :
            next().implementationName();
    }

    @Override
    public String implementationPackage() {
        return mapperConfig.implementationPackage().hasValue() ? mapperConfig.implementationPackage().get() :
            next().implementationPackage();
    }

    @Override
    public Set<DeclaredType> uses() {
        return toDeclaredTypes( mapperConfig.uses().get(), next().uses() );
    }

    @Override
    public Set<DeclaredType> imports() {
        return toDeclaredTypes( mapperConfig.imports().get(), next().imports() );
    }

    @Override
    public ReportingPolicyGem unmappedTargetPolicy() {
        return mapperConfig.unmappedTargetPolicy().hasValue() ?
            ReportingPolicyGem.valueOf( mapperConfig.unmappedTargetPolicy().get() ) : next().unmappedTargetPolicy();

    }

    @Override
    public ReportingPolicyGem unmappedSourcePolicy() {
        return mapperConfig.unmappedSourcePolicy().hasValue() ?
            ReportingPolicyGem.valueOf( mapperConfig.unmappedSourcePolicy().get() ) : next().unmappedSourcePolicy();
    }

    @Override
    public ReportingPolicyGem typeConversionPolicy() {
        return mapperConfig.typeConversionPolicy().hasValue() ?
            ReportingPolicyGem.valueOf( mapperConfig.typeConversionPolicy().get() ) : next().typeConversionPolicy();
    }

    @Override
    public String componentModel() {
        return mapperConfig.componentModel().hasValue() ? mapperConfig.componentModel().get() : next().componentModel();
    }

    @Override
    public boolean suppressTimestampInGenerated() {
        return mapperConfig.suppressTimestampInGenerated().hasValue() ?
            mapperConfig.suppressTimestampInGenerated().get() :
            next().suppressTimestampInGenerated();
    }

    @Override
    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        return mapperConfig.mappingInheritanceStrategy().hasValue() ?
            MappingInheritanceStrategyGem.valueOf( mapperConfig.mappingInheritanceStrategy().get() ) :
            next().getMappingInheritanceStrategy();
    }

    @Override
    public InjectionStrategyGem getInjectionStrategy() {
        return mapperConfig.injectionStrategy().hasValue() ?
            InjectionStrategyGem.valueOf( mapperConfig.injectionStrategy().get() ) :
            next().getInjectionStrategy();
    }

    @Override
    public Boolean isDisableSubMappingMethodsGeneration() {
        return mapperConfig.disableSubMappingMethodsGeneration().hasValue() ?
            mapperConfig.disableSubMappingMethodsGeneration().get() :
            next().isDisableSubMappingMethodsGeneration();
    }

    // @Mapping, @BeanMapping

    @Override
    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        return mapperConfig.collectionMappingStrategy().hasValue() ?
            CollectionMappingStrategyGem.valueOf( mapperConfig.collectionMappingStrategy().get() ) :
            next().getCollectionMappingStrategy();
    }

    @Override
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return mapperConfig.nullValueCheckStrategy().hasValue() ?
            NullValueCheckStrategyGem.valueOf( mapperConfig.nullValueCheckStrategy().get() ) :
            next().getNullValueCheckStrategy();
    }

    @Override
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return mapperConfig.nullValuePropertyMappingStrategy().hasValue() ?
            NullValuePropertyMappingStrategyGem.valueOf( mapperConfig.nullValuePropertyMappingStrategy().get() ) :
            next().getNullValuePropertyMappingStrategy();
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return mapperConfig.nullValueMappingStrategy().hasValue() ?
            NullValueMappingStrategyGem.valueOf( mapperConfig.nullValueMappingStrategy().get() ) :
            next().getNullValueMappingStrategy();
    }

    @Override
    public SubclassExhaustiveStrategyGem getSubclassExhaustiveStrategy() {
        return mapperConfig.subclassExhaustiveStrategy().hasValue() ?
            SubclassExhaustiveStrategyGem.valueOf( mapperConfig.subclassExhaustiveStrategy().get() ) :
            next().getSubclassExhaustiveStrategy();
    }

    public TypeMirror getSubclassExhaustiveException() {
        return mapperConfig.subclassExhaustiveException().hasValue() ?
            mapperConfig.subclassExhaustiveException().get() :
            next().getSubclassExhaustiveException();
    }

    @Override
    public NullValueMappingStrategyGem getNullValueIterableMappingStrategy() {
        if ( mapperConfig.nullValueIterableMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapperConfig.nullValueIterableMappingStrategy().get() );
        }
        if ( mapperConfig.nullValueMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapperConfig.nullValueMappingStrategy().get() );
        }
        return next().getNullValueIterableMappingStrategy();
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMapMappingStrategy() {
        if ( mapperConfig.nullValueMapMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapperConfig.nullValueMapMappingStrategy().get() );
        }
        if ( mapperConfig.nullValueMappingStrategy().hasValue() ) {
            return NullValueMappingStrategyGem.valueOf( mapperConfig.nullValueMappingStrategy().get() );
        }
        return next().getNullValueMapMappingStrategy();
    }

    @Override
    public BuilderGem getBuilder() {
        return mapperConfig.builder().hasValue() ? mapperConfig.builder().get() : next().getBuilder();
    }

    @Override
    public MappingControl getMappingControl(ElementUtils elementUtils) {
        return mapperConfig.mappingControl().hasValue() ?
            MappingControl.fromTypeMirror( mapperConfig.mappingControl().getValue(), elementUtils ) :
            next().getMappingControl( elementUtils );
    }

    @Override
    public TypeMirror getUnexpectedValueMappingException() {
        return mapperConfig.unexpectedValueMappingException().hasValue() ?
            mapperConfig.unexpectedValueMappingException().get() :
            next().getUnexpectedValueMappingException();
    }

    @Override
    public boolean hasAnnotation() {
        return mapperConfig != null;
    }

}
