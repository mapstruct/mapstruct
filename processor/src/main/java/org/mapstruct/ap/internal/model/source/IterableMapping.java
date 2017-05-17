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
package org.mapstruct.ap.internal.model.source;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.FormattingParameters;
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

    private final SelectionParameters selectionParameters;
    private final FormattingParameters formattingParameters;
    private final AnnotationMirror mirror;
    private final NullValueMappingStrategyPrism nullValueMappingStrategy;

    public static IterableMapping fromPrism(IterableMappingPrism iterableMapping, ExecutableElement method,
        FormattingMessager messager, Types typeUtils) {
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
            && iterableMapping.numberFormat().isEmpty()
            && iterableMapping.qualifiedBy().isEmpty()
            && iterableMapping.qualifiedByName().isEmpty()
            && ( nullValueMappingStrategy == null ) ) {

            messager.printMessage( method, Message.ITERABLEMAPPING_NO_ELEMENTS );
        }

        SelectionParameters selection = new SelectionParameters(
            iterableMapping.qualifiedBy(),
            iterableMapping.qualifiedByName(),
            elementTargetTypeIsDefined ? iterableMapping.elementTargetType() : null,
            typeUtils
        );

        FormattingParameters formatting = new FormattingParameters(
            iterableMapping.dateFormat(),
            iterableMapping.numberFormat(),
            iterableMapping.mirror,
            iterableMapping.values.dateFormat(),
            method
        );

        return new IterableMapping( formatting,
            selection,
            iterableMapping.mirror,
            nullValueMappingStrategy
        );
    }

    private IterableMapping(FormattingParameters formattingParameters, SelectionParameters selectionParameters,
        AnnotationMirror mirror, NullValueMappingStrategyPrism nvms) {

        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.mirror = mirror;
        this.nullValueMappingStrategy = nvms;
    }

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public FormattingParameters getFormattingParameters() {
        return formattingParameters;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return nullValueMappingStrategy;
    }
}
