/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.prism.MapperConfigPrism;
import org.mapstruct.ap.prism.MapperPrism;
import org.mapstruct.ap.prism.MappingInheritanceStrategyPrism;
import org.mapstruct.ap.prism.NullValueMappingStrategyPrism;

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

    public static MapperConfiguration getInstanceOn(Element e) {
        return new MapperConfiguration( MapperPrism.getInstanceOn( e ) );
    }

    private MapperConfiguration(MapperPrism mapperPrism) {
        this.mapperPrism = mapperPrism;
        TypeMirror typeMirror = mapperPrism.config();
        if ( typeMirror.getKind().equals( TypeKind.DECLARED ) ) {
            this.mapperConfigPrism = MapperConfigPrism.getInstanceOn( ( (DeclaredType) typeMirror ).asElement() );
        }
        else {
            this.mapperConfigPrism = null;
        }
    }

    public List<TypeMirror> uses() {
        Set<TypeMirror> uses = new LinkedHashSet<TypeMirror>( mapperPrism.uses() );
        if ( mapperConfigPrism != null ) {
            uses.addAll( mapperConfigPrism.uses() );
        }
        return new ArrayList<TypeMirror>( uses );
    }

    public List<TypeMirror> imports() {
        return mapperPrism.imports();
    }

    public String unmappedTargetPolicy() {
        if ( ReportingPolicy.valueOf( mapperPrism.unmappedTargetPolicy() ) != ReportingPolicy.DEFAULT ) {
            // it is not the default configuration
            return mapperPrism.unmappedTargetPolicy();
        }
        else if ( mapperConfigPrism != null &&
            ReportingPolicy.valueOf( mapperConfigPrism.unmappedTargetPolicy() ) != ReportingPolicy.DEFAULT ) {
            return mapperConfigPrism.unmappedTargetPolicy();
        }
        else {
            return ReportingPolicy.WARN.name();
        }
    }

    public CollectionMappingStrategyPrism getCollectionMappingStrategy() {
        CollectionMappingStrategyPrism mapperPolicy =
            CollectionMappingStrategyPrism.valueOf( mapperPrism.collectionMappingStrategy() );

        if ( mapperPolicy != CollectionMappingStrategyPrism.DEFAULT ) {
            // it is not the default mapper configuration, so return the mapper configured value
            return mapperPolicy;
        }
        else if ( mapperConfigPrism != null ) {
            // try the config mapper configuration
            CollectionMappingStrategyPrism configPolicy =
                CollectionMappingStrategyPrism.valueOf( mapperConfigPrism.collectionMappingStrategy() );
            if ( configPolicy != CollectionMappingStrategyPrism.DEFAULT ) {
                // its not the default configuration, so return the mapper config configured value
                return configPolicy;
            }
        }
        // when nothing specified, return ACCESSOR_ONLY (default option)
        return CollectionMappingStrategyPrism.ACCESSOR_ONLY;
    }

    public MappingInheritanceStrategyPrism getMappingInheritanceStrategy() {
        MappingInheritanceStrategyPrism mapperPolicy =
            MappingInheritanceStrategyPrism.valueOf( mapperPrism.mappingInheritanceStrategy() );

        if ( mapperPolicy != MappingInheritanceStrategyPrism.DEFAULT ) {
            return mapperPolicy;
        }
        else if ( mapperConfigPrism != null ) {
            MappingInheritanceStrategyPrism configPolicy =
                MappingInheritanceStrategyPrism.valueOf( mapperConfigPrism.mappingInheritanceStrategy() );
            if ( configPolicy != MappingInheritanceStrategyPrism.DEFAULT ) {
                return configPolicy;
            }
        }

        return MappingInheritanceStrategyPrism.EXPLICIT;
    }

    public boolean isMapToDefault(NullValueMappingStrategyPrism mapNullToDefault) {

        // check on method level
        if ( mapNullToDefault != null ) {
            if ( mapNullToDefault != NullValueMappingStrategyPrism.DEFAULT ) {
                return mapNullToDefault == NullValueMappingStrategyPrism.RETURN_DEFAULT;
            }
        }

        return isMapToDefaultOnMapperAndMappingConfigLevel();

    }

    private boolean isMapToDefaultOnMapperAndMappingConfigLevel() {

        // check on mapper level
        NullValueMappingStrategyPrism mapperPolicy =
            NullValueMappingStrategyPrism.valueOf( mapperPrism.nullValueMappingStrategy() );

        if ( mapperPolicy != NullValueMappingStrategyPrism.DEFAULT ) {
            // it is not the default mapper configuration, so return the mapper configured value
            return mapperPolicy == NullValueMappingStrategyPrism.RETURN_DEFAULT;
        }

        // check on mapping config level
        else if ( mapperConfigPrism != null ) {
            // try the config mapper configuration
            NullValueMappingStrategyPrism configPolicy =
                NullValueMappingStrategyPrism.valueOf( mapperConfigPrism.nullValueMappingStrategy() );
            if ( configPolicy != NullValueMappingStrategyPrism.DEFAULT ) {
                // its not the default configuration, so return the mapper config configured value
                return configPolicy == NullValueMappingStrategyPrism.RETURN_DEFAULT;
            }
        }
        // when nothing specified, return RETURN_NULL (default option)
        return false;
    }


    public String componentModel() {
        if ( !mapperPrism.componentModel().equals( "default" ) ) {
            return mapperPrism.componentModel();
        }
        else if ( mapperConfigPrism != null ) {
            return mapperConfigPrism.componentModel();
        }
        else {
            return "default";
        }
    }

    public TypeMirror getMapperConfigMirror() {
        return mapperPrism.config();
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
}
