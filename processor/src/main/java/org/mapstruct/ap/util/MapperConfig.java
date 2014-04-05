/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.prism.MapperConfigPrism;
import org.mapstruct.ap.prism.MapperPrism;

/**
 * Class decorating the {@link MapperPrism} with the 'default' configuration.
 *
 * If no configuration for a property is defined in the {@link org.mapstruct.Mapper} annotation this
 * decorator will revert to the {@link org.mapstruct.Mapper#config() } defined mapper.
 *
 * {@link org.mapstruct.MapperConfig#uses() } will add its Mappers to the ones defined in
 * {@link org.mapstruct.Mapper#uses() }
 *
 * @author Sjaak Derksen
 */
public class MapperConfig {

    private final MapperPrism mapperPrism;
    private final MapperConfigPrism mapperConfigPrism;

    public static MapperConfig getInstanceOn(Element e) {
        return new MapperConfig( MapperPrism.getInstanceOn( e ) );
    }

    public static MapperConfig getInstance(AnnotationMirror mirror ) {
        return new MapperConfig( MapperPrism.getInstance( mirror ) );
    }

    private MapperConfig( MapperPrism mapperPrism ) {
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
        Set<TypeMirror> uses = new HashSet<TypeMirror>( mapperPrism.uses() );
        if ( mapperConfigPrism != null ) {
            uses.addAll( mapperConfigPrism.uses() );
        }
        return new ArrayList<TypeMirror>( uses );
    }

    public String unmappedTargetPolicy() {
        if ( !ReportingPolicy.valueOf( mapperPrism.unmappedTargetPolicy() ).equals( ReportingPolicy.DEFAULT ) ) {
            // it is not the default configuration
            return mapperPrism.unmappedTargetPolicy();
        }
        else if ( mapperConfigPrism != null &&
                  !ReportingPolicy.valueOf( mapperConfigPrism.unmappedTargetPolicy())
                          .equals( ReportingPolicy.DEFAULT ) ) {
            return mapperConfigPrism.unmappedTargetPolicy();
        }
        else {
            return ReportingPolicy.WARN.name();
        }
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
