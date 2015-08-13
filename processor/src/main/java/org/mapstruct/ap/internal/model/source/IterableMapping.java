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
package org.mapstruct.ap.internal.model.source;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.prism.IterableMappingPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents an iterable mapping as configured via {@code @IterableMapping}.
 *
 * @author Gunnar Morling
 */
public class IterableMapping {

    private final String dateFormat;
    private final List<TypeMirror> qualifiers;
    private final TypeMirror qualifyingElementTargetType;
    private final AnnotationMirror mirror;
    private final AnnotationValue dateFormatAnnotationValue;
    private final NullValueMappingStrategyPrism nullValueMappingStrategy;

    public static IterableMapping fromPrism(IterableMappingPrism iterableMapping, ExecutableElement method,
                                            FormattingMessager messager) {
        if ( iterableMapping == null ) {
            return null;
        }

        boolean elementTargetTypeIsDefined = !TypeKind.VOID.equals( iterableMapping.elementTargetType().getKind() );

        NullValueMappingStrategyPrism nullValueMappingStrategy =
            iterableMapping.values.nullValueMappingStrategy() == null
                            ? null
                            : NullValueMappingStrategyPrism.valueOf( iterableMapping.nullValueMappingStrategy() );

        if ( !elementTargetTypeIsDefined
            && iterableMapping.dateFormat().isEmpty()
            && iterableMapping.qualifiedBy().isEmpty()
            && ( nullValueMappingStrategy == null ) ) {

            messager.printMessage( method, Message.ITERABLEMAPPING_NO_ELEMENTS );
        }

        return new IterableMapping(iterableMapping.dateFormat(),
            iterableMapping.qualifiedBy(),
            elementTargetTypeIsDefined ? iterableMapping.elementTargetType() : null,
            iterableMapping.mirror,
            iterableMapping.values.dateFormat(),
            nullValueMappingStrategy
        );
    }

    private IterableMapping(String dateFormat, List<TypeMirror> qualifiers, TypeMirror resultType,
        AnnotationMirror mirror, AnnotationValue dateFormatAnnotationValue, NullValueMappingStrategyPrism nvms) {

        this.dateFormat = dateFormat;
        this.qualifiers = qualifiers;
        this.qualifyingElementTargetType = resultType;
        this.mirror = mirror;
        this.dateFormatAnnotationValue = dateFormatAnnotationValue;
        this.nullValueMappingStrategy = nvms;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    public TypeMirror getQualifyingElementTargetType() {
        return qualifyingElementTargetType;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public AnnotationValue getDateFormatAnnotationValue() {
        return dateFormatAnnotationValue;
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return nullValueMappingStrategy;
    }

}
