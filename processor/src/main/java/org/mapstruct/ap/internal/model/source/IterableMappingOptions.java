/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
public class IterableMappingOptions {

    private final SelectionParameters selectionParameters;
    private final FormattingParameters formattingParameters;
    private final AnnotationMirror mirror;
    private final NullValueMappingStrategyPrism nullValueMappingStrategy;

    public static IterableMappingOptions fromPrism(IterableMappingPrism iterableMapping, ExecutableElement method,
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

        return new IterableMappingOptions( formatting,
            selection,
            iterableMapping.mirror,
            nullValueMappingStrategy
        );
    }

    private IterableMappingOptions(FormattingParameters formattingParameters, SelectionParameters selectionParameters,
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
