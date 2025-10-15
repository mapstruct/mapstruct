/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Optional;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.gem.IterableMappingGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.tools.gem.GemValue;

/**
 * Represents an iterable mapping as configured via {@code @IterableMapping}.
 *
 * @author Gunnar Morling
 */
public class IterableMappingOptions extends DelegatingOptions {

    private final SelectionParameters selectionParameters;
    private final FormattingParameters formattingParameters;
    private final IterableMappingGem iterableMapping;

    public static IterableMappingOptions fromGem(IterableMappingGem iterableMapping,
                                                 MapperOptions mapperOptions, ExecutableElement method,
                                                 FormattingMessager messager, TypeUtils typeUtils) {

        if ( iterableMapping == null || !isConsistent( iterableMapping, method, messager ) ) {
            IterableMappingOptions options = new IterableMappingOptions(
                null,
                SelectionParameters.empty(),
                null,
                mapperOptions
            );
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
            method,
            iterableMapping.locale().getValue()
        );

        IterableMappingOptions options =
            new IterableMappingOptions( formatting, selection, iterableMapping, mapperOptions );
        return options;
    }

    private static boolean isConsistent(IterableMappingGem gem, ExecutableElement method,
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
                                   IterableMappingGem iterableMapping,
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
        return Optional.ofNullable( iterableMapping ).map( IterableMappingGem::mirror ).orElse( null );
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return Optional.ofNullable( iterableMapping ).map( IterableMappingGem::nullValueMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueMappingStrategyGem::valueOf )
            .orElse( next().getNullValueIterableMappingStrategy() );
    }

    public MappingControl getElementMappingControl(ElementUtils elementUtils) {
        return Optional.ofNullable( iterableMapping ).map( IterableMappingGem::elementMappingControl )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( mc -> MappingControl.fromTypeMirror( mc, elementUtils ) )
            .orElse( next().getMappingControl( elementUtils ) );
    }

    @Override
    public boolean hasAnnotation() {
        return iterableMapping != null;
    }

}
