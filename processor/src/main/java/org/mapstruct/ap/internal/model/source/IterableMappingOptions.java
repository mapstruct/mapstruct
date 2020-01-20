/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Optional;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Types;

import org.mapstruct.annotations.GemValue;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.gem.IterableMappingGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
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
    private final Optional<IterableMappingGem.IterableMapping> iterableMapping;

    public static IterableMappingOptions fromGem(IterableMappingGem.IterableMapping iterableMapping,
                                                 MapperOptions mappperOptions, ExecutableElement method,
                                                 FormattingMessager messager, Types typeUtils) {

        if ( iterableMapping == null || !isConsistent( iterableMapping, method, messager ) ) {
            IterableMappingOptions options = new IterableMappingOptions( null, null, Optional.empty(), mappperOptions );
            return options;
        }

        SelectionParameters selection = new SelectionParameters(
            iterableMapping.qualifiedBy().get(),
            iterableMapping.qualifiedByName().get(),
            iterableMapping.elementTargetType().getValue(),
            typeUtils
        );

        FormattingParameters formatting = new FormattingParameters(
            iterableMapping.dateFormat().get(),
            iterableMapping.numberFormat().get(),
            iterableMapping.mirror(),
            iterableMapping.dateFormat().getAnnotationValue(),
            method
        );

        IterableMappingOptions options =
            new IterableMappingOptions( formatting, selection, Optional.ofNullable( iterableMapping ), mappperOptions );
        return options;
    }

    private static boolean isConsistent(IterableMappingGem.IterableMapping gem, ExecutableElement method,
                                        FormattingMessager messager) {
        if ( !gem.dateFormat().hasValue()
            && !gem.numberFormat().hasValue()
            && !gem.qualifiedBy().hasValue()
            && !gem.qualifiedByName().hasValue()
            && !gem.elementTargetType().hasValue()
            && !gem.nullValueMappingStrategy().hasValue() ) {
            messager.printMessage( method, Message.ITERABLEMAPPING_NO_ELEMENTS );
            return false;
        }
        return true;
    }

    private IterableMappingOptions(FormattingParameters formattingParameters, SelectionParameters selectionParameters,
                                   Optional<IterableMappingGem.IterableMapping> iterableMapping,
                                   DelegatingOptions next) {
        super( next );
        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.iterableMapping = iterableMapping;
    }

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public FormattingParameters getFormattingParameters() {
        return formattingParameters;
    }

    public AnnotationMirror getMirror() {
        return iterableMapping.map( IterableMappingGem.IterableMapping::mirror ).orElse( null );
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return iterableMapping.map( IterableMappingGem.IterableMapping::nullValueMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueMappingStrategyGem::valueOf )
            .orElse( next().getNullValueMappingStrategy() );
    }

    @Override
    public boolean hasAnnotation() {
        return iterableMapping.isPresent();
    }

}