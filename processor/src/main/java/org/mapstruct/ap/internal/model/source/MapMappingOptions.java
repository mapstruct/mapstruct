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
import org.mapstruct.ap.internal.prism.MapMappingPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents a map mapping as configured via {@code @MapMapping}.
 *
 * @author Gunnar Morling
 */
public class MapMappingOptions extends DelegatingOptions {

    private final SelectionParameters keySelectionParameters;
    private final SelectionParameters valueSelectionParameters;
    private final FormattingParameters keyFormattingParameters;
    private final FormattingParameters valueFormattingParameters;
    private final MapMappingPrism prism;

    public static MapMappingOptions fromPrism(MapMappingPrism prism, MapperOptions mapperOptions,
                                              ExecutableElement method,
                                              FormattingMessager messager, Types typeUtils) {

        if ( prism == null || !isConsistent( prism, method, messager ) ) {
            MapMappingOptions options = new MapMappingOptions( null, null, null, null, null, mapperOptions );
            return options;
        }

        SelectionParameters keySelection = new SelectionParameters(
            prism.keyQualifiedBy(),
            prism.keyQualifiedByName(),
            TypeKind.VOID != prism.keyTargetType().getKind() ? prism.keyTargetType() : null,
            typeUtils
        );

        SelectionParameters valueSelection = new SelectionParameters(
            prism.valueQualifiedBy(),
            prism.valueQualifiedByName(),
            TypeKind.VOID != prism.valueTargetType().getKind() ? prism.valueTargetType() : null,
            typeUtils
        );

        FormattingParameters keyFormatting = new FormattingParameters(
            prism.keyDateFormat(),
            prism.keyNumberFormat(),
            prism.mirror,
            prism.values.keyDateFormat(),
            method
        );

        FormattingParameters valueFormatting = new FormattingParameters(
            prism.valueDateFormat(),
            prism.valueNumberFormat(),
            prism.mirror,
            prism.values.valueDateFormat(),
            method
        );

        MapMappingOptions options = new MapMappingOptions(
            keyFormatting,
            keySelection,
            valueFormatting,
            valueSelection,
            prism,
            mapperOptions
        );
        return options;
    }

    private static boolean isConsistent(MapMappingPrism prism, ExecutableElement method, FormattingMessager messager) {
        if ( prism.keyDateFormat().isEmpty()
            && prism.keyNumberFormat().isEmpty()
            && prism.keyQualifiedBy().isEmpty()
            && prism.keyQualifiedByName().isEmpty()
            && prism.valueDateFormat().isEmpty()
            && prism.valueNumberFormat().isEmpty()
            && prism.valueQualifiedBy().isEmpty()
            && prism.valueQualifiedByName().isEmpty()
            && TypeKind.VOID == prism.keyTargetType().getKind()
            && TypeKind.VOID == prism.valueTargetType().getKind()
            && null == prism.values.nullValueMappingStrategy() ) {
            messager.printMessage( method, Message.MAPMAPPING_NO_ELEMENTS );
            return false;
        }
        return true;
    }

    private MapMappingOptions(FormattingParameters keyFormatting, SelectionParameters keySelectionParameters,
                              FormattingParameters valueFormatting, SelectionParameters valueSelectionParameters,
                              MapMappingPrism prism, DelegatingOptions next ) {
        super( next );
        this.keyFormattingParameters = keyFormatting;
        this.keySelectionParameters = keySelectionParameters;
        this.valueFormattingParameters = valueFormatting;
        this.valueSelectionParameters = valueSelectionParameters;
        this.prism = prism;
    }

    public FormattingParameters getKeyFormattingParameters() {
        return keyFormattingParameters;
    }

    public SelectionParameters getKeySelectionParameters() {
        return keySelectionParameters;
    }

    public FormattingParameters getValueFormattingParameters() {
        return valueFormattingParameters;
    }

    public SelectionParameters getValueSelectionParameters() {
        return valueSelectionParameters;
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
