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
package org.mapstruct.ap.model.source;

import java.util.List;
import org.mapstruct.ap.util.FormattingMessager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.prism.MapMappingPrism;
import org.mapstruct.ap.util.Message;

/**
 * Represents a map mapping as configured via {@code @MapMapping}.
 *
 * @author Gunnar Morling
 */
public class MapMapping {

    private final String keyFormat;
    private final List<TypeMirror> keyQualifiers;
    private final String valueFormat;
    private final List<TypeMirror> valueQualifiers;
    private final AnnotationMirror mirror;
    private final TypeMirror keyQualifyingTargetType;
    private final TypeMirror valueQualifyingTargetType;

    public static MapMapping fromPrism(MapMappingPrism mapMapping, ExecutableElement method,
                                       FormattingMessager messager) {
        if ( mapMapping == null ) {
            return null;
        }

        boolean keyTargetTypeIsDefined = !TypeKind.VOID.equals( mapMapping.keyTargetType().getKind() );
        boolean valueTargetTypeIsDefined = !TypeKind.VOID.equals( mapMapping.valueTargetType().getKind() );
        if ( mapMapping.keyDateFormat().isEmpty()
            && mapMapping.keyQualifiedBy().isEmpty()
            && mapMapping.valueDateFormat().isEmpty()
            && mapMapping.valueQualifiedBy().isEmpty()
            && !keyTargetTypeIsDefined
            && !valueTargetTypeIsDefined ) {
            messager.printMessage( method, Message.MAPMAPPING_NO_ELEMENTS );
        }


        return new MapMapping(
            mapMapping.keyDateFormat(),
            mapMapping.keyQualifiedBy(),
            keyTargetTypeIsDefined ? mapMapping.keyTargetType() : null,
            mapMapping.valueDateFormat(),
            mapMapping.valueQualifiedBy(),
            valueTargetTypeIsDefined ? mapMapping.valueTargetType() : null,
            mapMapping.mirror
        );
    }

    private MapMapping(
            String keyFormat,
            List<TypeMirror> keyQualifiers,
            TypeMirror keyResultType,
            String valueFormat,
            List<TypeMirror> valueQualifiers,
            TypeMirror valueResultType,
            AnnotationMirror mirror ) {
        this.keyFormat = keyFormat;
        this.keyQualifiers = keyQualifiers;
        this.keyQualifyingTargetType = keyResultType;
        this.valueFormat = valueFormat;
        this.valueQualifiers = valueQualifiers;
        this.valueQualifyingTargetType = valueResultType;
        this.mirror = mirror;
    }

    public String getKeyFormat() {
        return keyFormat;
    }

    public List<TypeMirror>  getKeyQualifiers() {
        return keyQualifiers;
    }

    public TypeMirror getKeyQualifyingTargetType() {
        return keyQualifyingTargetType;
    }

    public String getValueFormat() {
        return valueFormat;
    }

    public List<TypeMirror>  getValueQualifiers() {
        return valueQualifiers;
    }

    public TypeMirror getValueQualifyingTargetType() {
        return valueQualifyingTargetType;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }
}
