/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.Set;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MapperGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.gem.SubclassExhaustiveStrategyGem;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.ElementUtils;

public class DefaultOptions extends DelegatingOptions {

    private final MapperGem mapper;
    private final Options options;

    DefaultOptions(MapperGem mapper, Options options) {
        super( null );
        this.mapper = mapper;
        this.options = options;
    }

    @Override
    public String implementationName() {
        return mapper.implementationName().getDefaultValue();
    }

    @Override
    public String implementationPackage() {
        return mapper.implementationPackage().getDefaultValue();
    }

    @Override
    public Set<DeclaredType> uses() {
        return Collections.emptySet();
    }

    @Override
    public Set<DeclaredType> imports() {
        return Collections.emptySet();
    }

    @Override
    public ReportingPolicyGem unmappedTargetPolicy() {
        ReportingPolicyGem unmappedTargetPolicy = options.getUnmappedTargetPolicy();
        if ( unmappedTargetPolicy != null ) {
            return unmappedTargetPolicy;
        }
        return ReportingPolicyGem.valueOf( mapper.unmappedTargetPolicy().getDefaultValue() );
    }

    @Override
    public ReportingPolicyGem unmappedSourcePolicy() {
        ReportingPolicyGem unmappedSourcePolicy = options.getUnmappedSourcePolicy();
        if ( unmappedSourcePolicy != null ) {
            return unmappedSourcePolicy;
        }
        return ReportingPolicyGem.valueOf( mapper.unmappedSourcePolicy().getDefaultValue() );
    }

    @Override
    public ReportingPolicyGem typeConversionPolicy() {
        return ReportingPolicyGem.valueOf( mapper.typeConversionPolicy().getDefaultValue() );
    }

    @Override
    public String componentModel() {
        String defaultComponentModel = options.getDefaultComponentModel();
        if ( defaultComponentModel != null ) {
            return defaultComponentModel;
        }
        return mapper.componentModel().getDefaultValue();
    }

    public boolean suppressTimestampInGenerated() {
        if ( mapper.suppressTimestampInGenerated().hasValue() ) {
            return mapper.suppressTimestampInGenerated().getValue();
        }
        return options.isSuppressGeneratorTimestamp();
    }

    @Override
    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        return MappingInheritanceStrategyGem.valueOf( mapper.mappingInheritanceStrategy().getDefaultValue() );
    }

    @Override
    public InjectionStrategyGem getInjectionStrategy() {
        String defaultInjectionStrategy = options.getDefaultInjectionStrategy();
        if ( defaultInjectionStrategy != null ) {
            return InjectionStrategyGem.valueOf( defaultInjectionStrategy.toUpperCase() );
        }
        return InjectionStrategyGem.valueOf( mapper.injectionStrategy().getDefaultValue() );
    }

    @Override
    public Boolean isDisableSubMappingMethodsGeneration() {
        return mapper.disableSubMappingMethodsGeneration().getDefaultValue();
    }

    // BeanMapping and Mapping

    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        return CollectionMappingStrategyGem.valueOf( mapper.collectionMappingStrategy().getDefaultValue() );
    }

    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return NullValueCheckStrategyGem.valueOf( mapper.nullValueCheckStrategy().getDefaultValue() );
    }

    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return NullValuePropertyMappingStrategyGem.valueOf(
            mapper.nullValuePropertyMappingStrategy().getDefaultValue() );
    }

    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return NullValueMappingStrategyGem.valueOf( mapper.nullValueMappingStrategy().getDefaultValue() );
    }

    public SubclassExhaustiveStrategyGem getSubclassExhaustiveStrategy() {
        return SubclassExhaustiveStrategyGem.valueOf( mapper.subclassExhaustiveStrategy().getDefaultValue() );
    }

    public TypeMirror getSubclassExhaustiveException() {
        return mapper.subclassExhaustiveException().getDefaultValue();
    }

    public NullValueMappingStrategyGem getNullValueIterableMappingStrategy() {
        NullValueMappingStrategyGem nullValueIterableMappingStrategy = options.getNullValueIterableMappingStrategy();
        if ( nullValueIterableMappingStrategy != null ) {
            return nullValueIterableMappingStrategy;
        }
        return NullValueMappingStrategyGem.valueOf( mapper.nullValueIterableMappingStrategy().getDefaultValue() );
    }

    public NullValueMappingStrategyGem getNullValueMapMappingStrategy() {
        NullValueMappingStrategyGem nullValueMapMappingStrategy = options.getNullValueMapMappingStrategy();
        if ( nullValueMapMappingStrategy != null ) {
            return nullValueMapMappingStrategy;
        }
        return NullValueMappingStrategyGem.valueOf( mapper.nullValueMapMappingStrategy().getDefaultValue() );
    }

    public BuilderGem getBuilder() {
        // TODO: I realized this is not correct, however it needs to be null in order to keep downward compatibility
        // but assuming a default @Builder will make testcases fail. Not having a default means that you need to
        // specify this mandatory on @MapperConfig and @Mapper.
        return null;
    }

    @Override
    public MappingControl getMappingControl(ElementUtils elementUtils) {
        return MappingControl.fromTypeMirror( mapper.mappingControl().getDefaultValue(), elementUtils );
    }

    @Override
    public TypeMirror getUnexpectedValueMappingException() {
        return null;
    }

    @Override
    public boolean hasAnnotation() {
        return false;
    }

}
