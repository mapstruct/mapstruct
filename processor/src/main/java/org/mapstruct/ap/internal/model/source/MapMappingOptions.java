/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Optional;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.gem.MapMappingGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.tools.gem.GemValue;

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
    private final MapMappingGem mapMapping;

    public static MapMappingOptions fromGem(MapMappingGem mapMapping, MapperOptions mapperOptions,
                                            ExecutableElement method, FormattingMessager messager,
                                            TypeUtils typeUtils) {

        if ( mapMapping == null || !isConsistent( mapMapping, method, messager ) ) {
            MapMappingOptions options = new MapMappingOptions(
                null,
                SelectionParameters.empty(),
                null,
                SelectionParameters.empty(),
                null,
                mapperOptions
            );
            return options;
        }

        String locale = mapMapping.locale().getValue();

        SelectionParameters keySelection = new SelectionParameters(
            mapMapping.keyQualifiedBy().get(),
            mapMapping.keyQualifiedByName().get(),
            mapMapping.keyTargetType().getValue(),
            typeUtils
        );

        SelectionParameters valueSelection = new SelectionParameters(
            mapMapping.valueQualifiedBy().get(),
            mapMapping.valueQualifiedByName().get(),
            mapMapping.valueTargetType().getValue(),
            typeUtils
        );

        FormattingParameters keyFormatting = new FormattingParameters(
            mapMapping.keyDateFormat().get(),
            mapMapping.keyNumberFormat().get(),
            mapMapping.mirror(),
            mapMapping.keyDateFormat().getAnnotationValue(),
            method,
            locale
        );

        FormattingParameters valueFormatting = new FormattingParameters(
            mapMapping.valueDateFormat().get(),
            mapMapping.valueNumberFormat().get(),
            mapMapping.mirror(),
            mapMapping.valueDateFormat().getAnnotationValue(),
            method,
            locale
        );

        MapMappingOptions options = new MapMappingOptions(
            keyFormatting,
            keySelection,
            valueFormatting,
            valueSelection,
            mapMapping,
            mapperOptions
        );
        return options;
    }

    private static boolean isConsistent(MapMappingGem gem, ExecutableElement method,
                                        FormattingMessager messager) {
        if ( !gem.keyDateFormat().hasValue()
            && !gem.keyNumberFormat().hasValue()
            && !gem.keyQualifiedBy().hasValue()
            && !gem.keyQualifiedByName().hasValue()
            && !gem.valueDateFormat().hasValue()
            && !gem.valueNumberFormat().hasValue()
            && !gem.valueQualifiedBy().hasValue()
            && !gem.valueQualifiedByName().hasValue()
            && !gem.keyTargetType().hasValue()
            && !gem.valueTargetType().hasValue()
            && !gem.nullValueMappingStrategy().hasValue() ) {
            messager.printMessage( method, Message.MAPMAPPING_NO_ELEMENTS );
            return false;
        }
        return true;
    }

    private MapMappingOptions(FormattingParameters keyFormatting, SelectionParameters keySelectionParameters,
                              FormattingParameters valueFormatting, SelectionParameters valueSelectionParameters,
                              MapMappingGem mapMapping, DelegatingOptions next ) {
        super( next );
        this.keyFormattingParameters = keyFormatting;
        this.keySelectionParameters = keySelectionParameters;
        this.valueFormattingParameters = valueFormatting;
        this.valueSelectionParameters = valueSelectionParameters;
        this.mapMapping = mapMapping;
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
        return Optional.ofNullable( mapMapping ).map( MapMappingGem::mirror ).orElse( null );
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return Optional.ofNullable( mapMapping ).map( MapMappingGem::nullValueMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueMappingStrategyGem::valueOf )
            .orElse( next().getNullValueMapMappingStrategy() );
    }

    public MappingControl getKeyMappingControl(ElementUtils elementUtils) {
        return Optional.ofNullable( mapMapping ).map( MapMappingGem::keyMappingControl )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( mc -> MappingControl.fromTypeMirror( mc, elementUtils ) )
            .orElse( next().getMappingControl( elementUtils ) );
    }

    public MappingControl getValueMappingControl(ElementUtils elementUtils) {
        return Optional.ofNullable( mapMapping ).map( MapMappingGem::valueMappingControl )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( mc -> MappingControl.fromTypeMirror( mc, elementUtils ) )
            .orElse( next().getMappingControl( elementUtils ) );
    }

    @Override
    public boolean hasAnnotation() {
        return mapMapping != null;
    }

}
