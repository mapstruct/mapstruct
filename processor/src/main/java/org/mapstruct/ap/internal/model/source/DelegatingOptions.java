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
import javax.lang.model.util.Elements;

import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;

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

    public ReportingPolicyGem unmappedTargetPolicy() {
        return next.unmappedTargetPolicy();
    }

    public ReportingPolicyGem unmappedSourcePolicy() {
        return next.unmappedSourcePolicy();
    }

    public ReportingPolicyGem typeConversionPolicy() {
        return next.typeConversionPolicy();
    }

    public String componentModel() {
        return next.componentModel();
    }

    public MappingInheritanceStrategyGem getMappingInheritanceStrategy() {
        return next.getMappingInheritanceStrategy();
    }

    public InjectionStrategyGem getInjectionStrategy() {
        return next.getInjectionStrategy();
    }

    public Boolean isDisableSubMappingMethodsGeneration() {
        return next.isDisableSubMappingMethodsGeneration();
    }

    // BeanMapping and Mapping

    public CollectionMappingStrategyGem getCollectionMappingStrategy() {
        return next.getCollectionMappingStrategy();
    }

    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return next.getNullValueCheckStrategy();
    }

    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return next.getNullValuePropertyMappingStrategy();
    }

    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return next.getNullValueMappingStrategy();
    }

    public BuilderGem getBuilder() {
        return next.getBuilder();
    }

    public MappingControl getMappingControl(Elements elementUtils) {
        return next.getMappingControl( elementUtils );
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
