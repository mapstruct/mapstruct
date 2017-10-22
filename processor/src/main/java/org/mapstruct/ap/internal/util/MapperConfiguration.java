/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.InjectionStrategyPrism;
import org.mapstruct.ap.internal.prism.MapperConfigPrism;
import org.mapstruct.ap.internal.prism.MapperPrism;
import org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;

/**
 * Provides an aggregated view to the settings given via {@link org.mapstruct.Mapper} and
 * {@link org.mapstruct.MapperConfig} for a specific mapper class.
 * <p>
 * Settings given via {@code Mapper} will generally take precedence over settings inherited from a referenced config
 * class. The lists of referenced mappers given via {@link org.mapstruct.Mapper#uses()} and
 * {@link org.mapstruct.MapperConfig#uses() } will be merged.
 *
 * @author Sjaak Derksen
 */
public class MapperConfiguration {

    private final MapperPrism mapperPrism;
    private final MapperConfigPrism mapperConfigPrism;
    private final DeclaredType config;

    public static MapperConfiguration getInstanceOn(Element e) {
        return new MapperConfiguration( MapperPrism.getInstanceOn( e ) );
    }

    private MapperConfiguration(MapperPrism mapperPrism) {
        this.mapperPrism = mapperPrism;

        if ( mapperPrism.values.config() != null ) {
            // TODO #737 Only a declared type makes sense here; Validate and raise graceful error;
            // Also validate that @MapperConfig is present
            this.config = (DeclaredType) mapperPrism.config();
            this.mapperConfigPrism = MapperConfigPrism.getInstanceOn( config.asElement() );
        }
        else {
            this.config = null;
            this.mapperConfigPrism = null;
        }
    }

    public String implementationName() {
        if ( mapperConfigPrism != null && mapperPrism.values.implementationName() == null ) {
            return mapperConfigPrism.implementationName();
        }
        else {
            return mapperPrism.implementationName();
        }
    }

    public String implementationPackage() {
        if ( mapperConfigPrism != null && mapperPrism.values.implementationPackage() == null ) {
            return mapperConfigPrism.implementationPackage();
        }
        else {
            return mapperPrism.implementationPackage();
        }
    }

    public Set<DeclaredType> uses() {
        Set<DeclaredType> uses = new LinkedHashSet<DeclaredType>();

        for ( TypeMirror usedMapperType : mapperPrism.uses() ) {
            // TODO #737 Only declared type make sense here; Validate and raise graceful error;
            uses.add( (DeclaredType) usedMapperType );
        }

        if ( mapperConfigPrism != null ) {
            for ( TypeMirror usedMapperType : mapperConfigPrism.uses() ) {
                // TODO #737 Only declared type make sense here; Validate and raise graceful error;
                uses.add( (DeclaredType) usedMapperType );
            }
        }

        return uses;
    }

    public List<TypeMirror> imports() {
        return mapperPrism.imports();
    }

    public ReportingPolicyPrism unmappedTargetPolicy(Options options) {
        if ( mapperPrism.values.unmappedTargetPolicy() != null ) {
            return ReportingPolicyPrism.valueOf( mapperPrism.unmappedTargetPolicy() );
        }

        if ( mapperConfigPrism != null && mapperConfigPrism.values.unmappedTargetPolicy() != null ) {
            return ReportingPolicyPrism.valueOf( mapperConfigPrism.unmappedTargetPolicy() );
        }

        if ( options.getUnmappedTargetPolicy() != null ) {
            return options.getUnmappedTargetPolicy();
        }

        // fall back to default defined in the annotation
        return ReportingPolicyPrism.valueOf( mapperPrism.unmappedTargetPolicy() );
    }

    public ReportingPolicyPrism unmappedSourcePolicy() {
        if ( mapperPrism.values.unmappedSourcePolicy() != null ) {
            return ReportingPolicyPrism.valueOf( mapperPrism.unmappedSourcePolicy() );
        }

        if ( mapperConfigPrism != null && mapperConfigPrism.values.unmappedSourcePolicy() != null ) {
            return ReportingPolicyPrism.valueOf( mapperConfigPrism.unmappedSourcePolicy() );
        }

        // fall back to default defined in the annotation
        return ReportingPolicyPrism.valueOf( mapperPrism.unmappedSourcePolicy() );
    }

    public CollectionMappingStrategyPrism getCollectionMappingStrategy() {
        if ( mapperConfigPrism != null && mapperPrism.values.collectionMappingStrategy() == null ) {
            return CollectionMappingStrategyPrism.valueOf( mapperConfigPrism.collectionMappingStrategy() );
        }
        else {
            return CollectionMappingStrategyPrism.valueOf( mapperPrism.collectionMappingStrategy() );
        }
    }

    public MappingInheritanceStrategyPrism getMappingInheritanceStrategy() {
        if ( mapperConfigPrism != null && mapperPrism.values.mappingInheritanceStrategy() == null ) {
            return MappingInheritanceStrategyPrism.valueOf( mapperConfigPrism.mappingInheritanceStrategy() );
        }
        else {
            return MappingInheritanceStrategyPrism.valueOf( mapperPrism.mappingInheritanceStrategy() );
        }
    }

    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        if ( mapperConfigPrism != null && mapperPrism.values.nullValueCheckStrategy() == null ) {
            return NullValueCheckStrategyPrism.valueOf( mapperConfigPrism.nullValueCheckStrategy() );
        }
        else {
            return NullValueCheckStrategyPrism.valueOf( mapperPrism.nullValueCheckStrategy() );
        }
    }

    public InjectionStrategyPrism getInjectionStrategy() {
        if ( mapperConfigPrism != null && mapperPrism.values.injectionStrategy() == null ) {
            return InjectionStrategyPrism.valueOf( mapperConfigPrism.injectionStrategy() );
        }
        else {
            return InjectionStrategyPrism.valueOf( mapperPrism.injectionStrategy() );
        }
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        if ( mapperConfigPrism != null && mapperPrism.values.nullValueMappingStrategy() == null ) {
            return NullValueMappingStrategyPrism.valueOf( mapperConfigPrism.nullValueMappingStrategy() );
        }
        else {
            return NullValueMappingStrategyPrism.valueOf( mapperPrism.nullValueMappingStrategy() );
        }
    }

    public boolean isMapToDefault(NullValueMappingStrategyPrism mapNullToDefault) {

        // check on method level
        if ( mapNullToDefault != null ) {
            return mapNullToDefault == NullValueMappingStrategyPrism.RETURN_DEFAULT;
        }

        return isMapToDefaultOnMapperAndMappingConfigLevel();

    }

    private boolean isMapToDefaultOnMapperAndMappingConfigLevel() {
        final NullValueMappingStrategyPrism strategy;
        if ( mapperConfigPrism != null && mapperPrism.values.nullValueMappingStrategy() == null ) {
            strategy = NullValueMappingStrategyPrism.valueOf( mapperConfigPrism.nullValueMappingStrategy() );
        }
        else {
            strategy = NullValueMappingStrategyPrism.valueOf( mapperPrism.nullValueMappingStrategy() );
        }

        return NullValueMappingStrategyPrism.RETURN_DEFAULT == strategy;
    }

    public String componentModel(Options options) {
        if ( mapperPrism.values.componentModel() != null ) {
            return mapperPrism.componentModel();
        }

        if ( mapperConfigPrism != null && mapperConfigPrism.values.componentModel() != null ) {
            return mapperConfigPrism.componentModel();
        }

        if ( options.getDefaultComponentModel() != null ) {
            return options.getDefaultComponentModel();
        }

        return mapperPrism.componentModel(); // fall back to default defined in the annotation
    }

    public boolean isDisableSubMappingMethodsGeneration() {
        if ( mapperPrism.disableSubMappingMethodsGeneration() ) {
            return mapperPrism.disableSubMappingMethodsGeneration();
        }

        if ( mapperConfigPrism != null && mapperConfigPrism.disableSubMappingMethodsGeneration() ) {
            return mapperConfigPrism.disableSubMappingMethodsGeneration();
        }

        return mapperPrism.disableSubMappingMethodsGeneration(); // fall back to default defined in the annotation
    }

    public DeclaredType config() {
        return config;
    }

    public boolean isValid() {
        return mapperPrism.isValid;
    }

    public AnnotationMirror getAnnotationMirror() {
        return mapperPrism.mirror;
    }
}
