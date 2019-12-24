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
public class IterableMappingOptions extends DelegatingOptions {

    private final SelectionParameters selectionParameters;
    private final FormattingParameters formattingParameters;
    private final IterableMappingPrism prism;

    public static IterableMappingOptions fromPrism(IterableMappingPrism prism,
                                                   MapperOptions mappperOptions, ExecutableElement method,
                                                   FormattingMessager messager, Types typeUtils) {

        if ( prism == null || !isConsistent( prism, method, messager ) ) {
            IterableMappingOptions options = new IterableMappingOptions( null, null, null, mappperOptions );
            return options;
        }

        SelectionParameters selection = new SelectionParameters(
            prism.qualifiedBy(),
            prism.qualifiedByName(),
            TypeKind.VOID != prism.elementTargetType().getKind() ? prism.elementTargetType() : null,
            typeUtils
        );

        FormattingParameters formatting = new FormattingParameters(
            prism.dateFormat(),
            prism.numberFormat(),
            prism.mirror,
            prism.values.dateFormat(),
            method
        );

        IterableMappingOptions options = new IterableMappingOptions( formatting, selection, prism, mappperOptions );
        return options;
    }

    private static boolean isConsistent(IterableMappingPrism prism, ExecutableElement method,
                                        FormattingMessager messager) {
        if ( prism.dateFormat().isEmpty()
            && prism.numberFormat().isEmpty()
            && prism.qualifiedBy().isEmpty()
            && prism.qualifiedByName().isEmpty()
            && TypeKind.VOID == prism.elementTargetType().getKind()
            && null == prism.values.nullValueMappingStrategy() ) {
            messager.printMessage( method, Message.ITERABLEMAPPING_NO_ELEMENTS );
            return false;
        }
        return true;
    }

    private IterableMappingOptions(FormattingParameters formattingParameters, SelectionParameters selectionParameters,
                                   IterableMappingPrism prism, DelegatingOptions next ) {
        super( next );
        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.prism = prism;
    }

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public FormattingParameters getFormattingParameters() {
        return formattingParameters;
    }

    public AnnotationMirror getMirror() {
        return null == prism ? null : prism.mirror;
    }

    @Override
    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return null == prism || null == prism.values.nullValueMappingStrategy() ?
            next().getNullValueMappingStrategy()
            : NullValueMappingStrategyPrism.valueOf( prism.nullValueMappingStrategy() );
    }

    @Override
    public boolean hasAnnotation() {
        return prism != null;
    }

}
