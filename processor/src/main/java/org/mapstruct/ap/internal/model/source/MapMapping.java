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
public class MapMapping {

    private final SelectionParameters keySelectionParameters;
    private final SelectionParameters valueSelectionParameters;
    private final FormattingParameters keyFormattingParameters;
    private final FormattingParameters valueFormattingParameters;
    private final AnnotationMirror mirror;
    private final NullValueMappingStrategyPrism nullValueMappingStrategy;

    public static MapMapping fromPrism(MapMappingPrism mapMapping, ExecutableElement method,
        FormattingMessager messager, Types typeUtils) {
        if ( mapMapping == null ) {
            return null;
        }

        NullValueMappingStrategyPrism nullValueMappingStrategy =
            mapMapping.values.nullValueMappingStrategy() == null
                            ? null
                            : NullValueMappingStrategyPrism.valueOf( mapMapping.nullValueMappingStrategy() );


        boolean keyTargetTypeIsDefined = !TypeKind.VOID.equals( mapMapping.keyTargetType().getKind() );
        boolean valueTargetTypeIsDefined = !TypeKind.VOID.equals( mapMapping.valueTargetType().getKind() );
        if ( mapMapping.keyDateFormat().isEmpty()
            && mapMapping.keyNumberFormat().isEmpty()
            && mapMapping.keyQualifiedBy().isEmpty()
            && mapMapping.keyQualifiedByName().isEmpty()
            && mapMapping.valueDateFormat().isEmpty()
            && mapMapping.valueNumberFormat().isEmpty()
            && mapMapping.valueQualifiedBy().isEmpty()
            && mapMapping.valueQualifiedByName().isEmpty()
            && !keyTargetTypeIsDefined
            && !valueTargetTypeIsDefined
            && ( nullValueMappingStrategy == null ) ) {

            messager.printMessage( method, Message.MAPMAPPING_NO_ELEMENTS );
        }

        SelectionParameters keySelection = new SelectionParameters(
            mapMapping.keyQualifiedBy(),
            mapMapping.keyQualifiedByName(),
            keyTargetTypeIsDefined ? mapMapping.keyTargetType() : null,
            typeUtils
        );

        SelectionParameters valueSelection = new SelectionParameters(
            mapMapping.valueQualifiedBy(),
            mapMapping.valueQualifiedByName(),
            valueTargetTypeIsDefined ? mapMapping.valueTargetType() : null,
            typeUtils
        );

        FormattingParameters keyFormatting = new FormattingParameters(
            mapMapping.keyDateFormat(),
            mapMapping.keyNumberFormat(),
            mapMapping.mirror,
            mapMapping.values.keyDateFormat(),
            method
        );

        FormattingParameters valueFormatting = new FormattingParameters(
            mapMapping.valueDateFormat(),
            mapMapping.valueNumberFormat(),
            mapMapping.mirror,
            mapMapping.values.valueDateFormat(),
            method
        );

        return new MapMapping(
            keyFormatting,
            keySelection,
            valueFormatting,
            valueSelection,
            mapMapping.mirror,
            nullValueMappingStrategy
        );
    }

    private MapMapping(FormattingParameters keyFormatting, SelectionParameters keySelectionParameters,
        FormattingParameters valueFormatting, SelectionParameters valueSelectionParameters, AnnotationMirror mirror,
        NullValueMappingStrategyPrism nvms ) {
        this.keyFormattingParameters = keyFormatting;
        this.keySelectionParameters = keySelectionParameters;
        this.valueFormattingParameters = valueFormatting;
        this.valueSelectionParameters = valueSelectionParameters;
        this.mirror = mirror;
        this.nullValueMappingStrategy = nvms;
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
        return mirror;
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return nullValueMappingStrategy;
    }

}
