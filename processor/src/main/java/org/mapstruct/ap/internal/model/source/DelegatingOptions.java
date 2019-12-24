/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.prism.BuilderPrism;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.InjectionStrategyPrism;
import org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValuePropertyMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;

/**
 * Chain Of Responsibility Pattern.
 */
public abstract class DelegatingOptions {

    private final DelegatingOptions next;

    public DelegatingOptions(DelegatingOptions next) {
        this.next = next;
    }

    // @Mapper and @MapperConfig

    public String implementationName() {
        return next.implementationName();
    }

    public String implementationPackage() {
        return next.implementationPackage();
    }

    public Set<DeclaredType> uses() {
        return next.uses();
    }

    public Set<DeclaredType> imports() {
        return next.imports();
    }

    public ReportingPolicyPrism unmappedTargetPolicy() {
        return next.unmappedTargetPolicy();
    }

    public ReportingPolicyPrism unmappedSourcePolicy() {
        return next.unmappedSourcePolicy();
    }

    public ReportingPolicyPrism typeConversionPolicy() {
        return next.typeConversionPolicy();
    }

    public String componentModel() {
        return next.componentModel();
    }

    public MappingInheritanceStrategyPrism getMappingInheritanceStrategy() {
        return next.getMappingInheritanceStrategy();
    }

    public InjectionStrategyPrism getInjectionStrategy() {
        return next.getInjectionStrategy();
    }

    public Boolean isDisableSubMappingMethodsGeneration() {
        return next.isDisableSubMappingMethodsGeneration();
    }

    // BeanMapping and Mapping

    public CollectionMappingStrategyPrism getCollectionMappingStrategy() {
        return next.getCollectionMappingStrategy();
    }

    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        return next.getNullValueCheckStrategy();
    }

    public NullValuePropertyMappingStrategyPrism getNullValuePropertyMappingStrategy() {
        return next.getNullValuePropertyMappingStrategy();
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return next.getNullValueMappingStrategy();
    }

    public BuilderPrism getBuilderPrism() {
        return next.getBuilderPrism();
    }

    DelegatingOptions next() {
        return next;
    }

    protected Set<DeclaredType> toDeclaredTypes(List<TypeMirror> in, Set<DeclaredType> next) {
        Set result = in.stream()
            .map( DeclaredType.class::cast )
            .collect( Collectors.toCollection( LinkedHashSet::new ) );
        result.addAll( next );
        return result;
    }

    public abstract boolean hasAnnotation();

}
