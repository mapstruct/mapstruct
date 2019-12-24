/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.Set;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.prism.BuilderPrism;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.InjectionStrategyPrism;
import org.mapstruct.ap.internal.prism.MapperPrism;
import org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValuePropertyMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;

public class DefaultOptions extends DelegatingOptions {

    private final MapperPrism prism;
    private final Options options;

    DefaultOptions(MapperPrism prism, Options options) {
        super( null );
        this.prism = prism;
        this.options = options;
    }

    @Override
    public String implementationName() {
        return prism.implementationName();
    }

    @Override
    public String implementationPackage() {
        return prism.implementationPackage();
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
    public ReportingPolicyPrism unmappedTargetPolicy() {
        if ( options.getUnmappedTargetPolicy() != null ) {
            return options.getUnmappedTargetPolicy();
        }
        return ReportingPolicyPrism.valueOf( prism.unmappedTargetPolicy() );
    }

    @Override
    public ReportingPolicyPrism unmappedSourcePolicy() {
        return ReportingPolicyPrism.valueOf( prism.unmappedSourcePolicy() );
    }

    @Override
    public ReportingPolicyPrism typeConversionPolicy() {
        return ReportingPolicyPrism.valueOf( prism.typeConversionPolicy() );
    }

    @Override
    public String componentModel() {
        if ( options.getDefaultComponentModel() != null ) {
            return options.getDefaultComponentModel();
        }
        return prism.componentModel();
    }

    @Override
    public MappingInheritanceStrategyPrism getMappingInheritanceStrategy() {
        return MappingInheritanceStrategyPrism.valueOf( prism.mappingInheritanceStrategy() );
    }

    @Override
    public InjectionStrategyPrism getInjectionStrategy() {
        if ( options.getDefaultInjectionStrategy() != null ) {
            return InjectionStrategyPrism.valueOf( options.getDefaultInjectionStrategy().toUpperCase() );
        }
        return InjectionStrategyPrism.valueOf( prism.injectionStrategy() );
    }

    @Override
    public Boolean isDisableSubMappingMethodsGeneration() {
        return prism.disableSubMappingMethodsGeneration();
    }

    // BeanMapping and Mapping

    public CollectionMappingStrategyPrism getCollectionMappingStrategy() {
        return CollectionMappingStrategyPrism.valueOf( prism.collectionMappingStrategy() );
    }

    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        return NullValueCheckStrategyPrism.valueOf( prism.nullValueCheckStrategy() );
    }

    public NullValuePropertyMappingStrategyPrism getNullValuePropertyMappingStrategy() {
        return NullValuePropertyMappingStrategyPrism.valueOf( prism.nullValuePropertyMappingStrategy() );
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return NullValueMappingStrategyPrism.valueOf( prism.nullValueMappingStrategy() );
    }

    public BuilderPrism getBuilderPrism() {
        // TODO: I realized this is not correct, however it needs to be null in order to keep downward compatibility
        // but assuming a default @Builder will make testcases fail. Not having a default means that you need to
        // specify this mandatory on @MappingConfig and @Mapper.
        return null;
    }

    @Override
    public boolean hasAnnotation() {
        return false;
    }

}
