/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Map;
import java.util.Optional;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.EnumMappingGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

import static org.mapstruct.ap.internal.util.Message.ENUMMAPPING_INCORRECT_TRANSFORMATION_STRATEGY;
import static org.mapstruct.ap.internal.util.Message.ENUMMAPPING_MISSING_CONFIGURATION;
import static org.mapstruct.ap.internal.util.Message.ENUMMAPPING_NO_ELEMENTS;

/**
 * Represents an enum mapping as configured via {@code @EnumMapping}.
 *
 * @author Filip Hrisafov
 */
public class EnumMappingOptions extends DelegatingOptions {

    private final EnumMappingGem enumMapping;
    private final boolean inverse;
    private final boolean valid;

    private EnumMappingOptions(EnumMappingGem enumMapping, boolean inverse, boolean valid, DelegatingOptions next) {
        super( next );
        this.enumMapping = enumMapping;
        this.inverse = inverse;
        this.valid = valid;
    }

    @Override
    public boolean hasAnnotation() {
        return enumMapping != null;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean hasNameTransformationStrategy() {
        return hasAnnotation() && Strings.isNotEmpty( getNameTransformationStrategy() );
    }

    public String getNameTransformationStrategy() {
        return enumMapping.nameTransformationStrategy().getValue();
    }

    public String getNameTransformationConfiguration() {
        return enumMapping.configuration().getValue();
    }

    @Override
    public TypeMirror getUnexpectedValueMappingException() {
        if ( enumMapping != null && enumMapping.unexpectedValueMappingException().hasValue() ) {
            return enumMapping.unexpectedValueMappingException().getValue();
        }

        return next().getUnexpectedValueMappingException();
    }

    public AnnotationMirror getMirror() {
        return Optional.ofNullable( enumMapping ).map( EnumMappingGem::mirror ).orElse( null );
    }

    public boolean isInverse() {
        return inverse;
    }

    public EnumMappingOptions inverse() {
        return new EnumMappingOptions( enumMapping, true, valid, next() );
    }

    public static EnumMappingOptions getInstanceOn(ExecutableElement method, MapperOptions mapperOptions,
        Map<String, EnumTransformationStrategy> enumTransformationStrategies, FormattingMessager messager) {

        EnumMappingGem enumMapping = EnumMappingGem.instanceOn( method );
        if ( enumMapping == null ) {
            return new EnumMappingOptions( null, false, true, mapperOptions );
        }
        else if ( !isConsistent( enumMapping, method, enumTransformationStrategies, messager ) ) {
            return new EnumMappingOptions( null, false, false, mapperOptions );
        }

        return new EnumMappingOptions(
            enumMapping,
            false,
            true,
            mapperOptions
        );
    }

    private static boolean isConsistent(EnumMappingGem gem, ExecutableElement method,
        Map<String, EnumTransformationStrategy> enumTransformationStrategies, FormattingMessager messager) {

        String strategy = gem.nameTransformationStrategy().getValue();
        String configuration = gem.configuration().getValue();

        boolean isConsistent = false;

        if ( Strings.isNotEmpty( strategy ) || Strings.isNotEmpty( configuration ) ) {
            if ( !enumTransformationStrategies.containsKey( strategy ) ) {
                String registeredStrategies = Strings.join( enumTransformationStrategies.keySet(), ", " );
                messager.printMessage(
                    method,
                    gem.mirror(),
                    gem.nameTransformationStrategy().getAnnotationValue(),
                    ENUMMAPPING_INCORRECT_TRANSFORMATION_STRATEGY,
                    strategy,
                    registeredStrategies
                );

                return false;
            }
            else if ( Strings.isEmpty( configuration ) ) {
                messager.printMessage(
                    method,
                    gem.mirror(),
                    gem.configuration().getAnnotationValue(),
                    ENUMMAPPING_MISSING_CONFIGURATION
                );
                return false;
            }

            isConsistent = true;
        }

        isConsistent = isConsistent || gem.unexpectedValueMappingException().hasValue();

        if ( !isConsistent ) {
            messager.printMessage(
                method,
                gem.mirror(),
                ENUMMAPPING_NO_ELEMENTS
            );
        }

        return isConsistent;
    }
}
