/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.MapperConfigPrism;
import org.mapstruct.ap.internal.prism.MapperPrism;
import org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;

import org.mapstruct.ap.internal.prism.SourceValuePresenceCheckStrategy;

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

    public String unmappedTargetPolicy() {
        if ( mapperConfigPrism != null && mapperPrism.values.unmappedTargetPolicy() == null ) {
            return mapperConfigPrism.unmappedTargetPolicy();
        }
        else {
            return mapperPrism.unmappedTargetPolicy();
        }
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


    public String componentModel() {
        if ( mapperConfigPrism != null && mapperPrism.values.componentModel() == null ) {
            return mapperConfigPrism.componentModel();
        }
        else {
            return mapperPrism.componentModel();
        }
    }

    public DeclaredType config() {
        return config;
    }

    public boolean isValid() {
        return mapperPrism.isValid;
    }

    public boolean isSetUnmappedTargetPolicy() {
        return mapperPrism.values.unmappedTargetPolicy() != null;
    }

    public AnnotationMirror getAnnotationMirror() {
        return mapperPrism.mirror;
    }

    public SourceValuePresenceCheckStrategy sourceValuePresenceCheckStrategy() {
        if ( mapperConfigPrism != null && mapperPrism.values.sourceValuePresenceCheckStrategy() == null ) {
             return SourceValuePresenceCheckStrategy.valueOf( mapperConfigPrism.sourceValuePresenceCheckStrategy() );
        }
        else {
             return SourceValuePresenceCheckStrategy.valueOf( mapperPrism.sourceValuePresenceCheckStrategy() );
        }
    }

    public boolean isSetSourceValuePresenceCheckStrategy() {
       if ( mapperConfigPrism != null  && mapperPrism.values.sourceValuePresenceCheckStrategy() == null ) {
            return  mapperConfigPrism.values.sourceValuePresenceCheckStrategy() != null;
       }
       else {
            return  mapperPrism.values.sourceValuePresenceCheckStrategy() != null;
       }
    }
}
