/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Set;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.prism.BuilderPrism;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.InjectionStrategyPrism;
import org.mapstruct.ap.internal.prism.MapperConfigPrism;
import org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValuePropertyMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;

public class MapperConfigOptions extends DelegatingOptions {

    private final MapperConfigPrism prism;

    MapperConfigOptions(MapperConfigPrism prism, DelegatingOptions next ) {
        super( next );
        this.prism = prism;
    }

    @Override
    public String implementationName() {
        return null == prism.values.implementationName() ? next().implementationName() :
            prism.implementationName();
    }

    @Override
    public String implementationPackage() {
        return null == prism.values.implementationPackage() ? next().implementationPackage() :
            prism.implementationPackage();
    }

    @Override
    public Set<DeclaredType> uses() {
        return toDeclaredTypes( prism.uses(), next().uses() );
    }

    @Override
    public Set<DeclaredType> imports() {
        return toDeclaredTypes( prism.imports(), next().imports() );
    }

    @Override
    public ReportingPolicyPrism unmappedTargetPolicy() {
        return null == prism.values.unmappedTargetPolicy() ? next().unmappedTargetPolicy() :
            ReportingPolicyPrism.valueOf( prism.unmappedTargetPolicy() );
    }

    @Override
    public ReportingPolicyPrism unmappedSourcePolicy() {
        return null == prism.values.unmappedSourcePolicy() ? next().unmappedSourcePolicy() :
        ReportingPolicyPrism.valueOf( prism.unmappedSourcePolicy() );
    }

    @Override
    public ReportingPolicyPrism typeConversionPolicy() {
        return null == prism.values.typeConversionPolicy() ? next().typeConversionPolicy() :
        ReportingPolicyPrism.valueOf( prism.typeConversionPolicy() );
    }

    @Override
    public String componentModel() {
        return null == prism.values.componentModel() ? next().componentModel() : prism.componentModel();
    }

    @Override
    public MappingInheritanceStrategyPrism getMappingInheritanceStrategy() {
        return null == prism.values.mappingInheritanceStrategy() ? next().getMappingInheritanceStrategy() :
            MappingInheritanceStrategyPrism.valueOf( prism.mappingInheritanceStrategy() );
    }

    @Override
    public InjectionStrategyPrism getInjectionStrategy() {
        return null == prism.values.injectionStrategy() ? next().getInjectionStrategy() :
            InjectionStrategyPrism.valueOf( prism.injectionStrategy() );
    }

    @Override
    public Boolean isDisableSubMappingMethodsGeneration() {
        return null == prism.values.disableSubMappingMethodsGeneration() ?
            next().isDisableSubMappingMethodsGeneration() : prism.disableSubMappingMethodsGeneration();
    }

    @Override
    public CollectionMappingStrategyPrism getCollectionMappingStrategy() {
        return null == prism.values.collectionMappingStrategy() ?
            next().getCollectionMappingStrategy()
            : CollectionMappingStrategyPrism.valueOf( prism.collectionMappingStrategy() );
    }

    @Override
    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        return null == prism.values.nullValueCheckStrategy() ?
            next().getNullValueCheckStrategy()
            : NullValueCheckStrategyPrism.valueOf( prism.nullValueCheckStrategy() );
    }

    @Override
    public NullValuePropertyMappingStrategyPrism getNullValuePropertyMappingStrategy() {
        return null == prism.values.nullValuePropertyMappingStrategy() ?
            next().getNullValuePropertyMappingStrategy()
            : NullValuePropertyMappingStrategyPrism.valueOf( prism.nullValuePropertyMappingStrategy() );
    }

    @Override
    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return null == prism.values.nullValueMappingStrategy() ?
            next().getNullValueMappingStrategy()
            : NullValueMappingStrategyPrism.valueOf( prism.nullValueMappingStrategy() );
    }

    @Override
    public BuilderPrism getBuilderPrism() {
        return null == prism.values.builder() ? next().getBuilderPrism() : prism.builder();
    }

    @Override
    public boolean hasAnnotation() {
        return prism != null;
    }

}
