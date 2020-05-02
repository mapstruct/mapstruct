/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Map;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.gem.EnumMappingGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

import static org.mapstruct.ap.internal.util.Message.ENUMMAPPING_INCORRECT_TRANSFORMATION_STRATEGY;

/**
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

    public String getNameTransformationStrategy() {
        return enumMapping.nameTransformStrategy().get();
    }

    public String getNameTransformationConfiguration() {
        return enumMapping.configuration().get();
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

        String strategy = gem.nameTransformStrategy().getValue();

        if ( !enumTransformationStrategies.containsKey( strategy ) ) {
            String registeredStrategies = Strings.join( enumTransformationStrategies.keySet(), ", " );
            messager.printMessage(
                method,
                gem.mirror(),
                gem.nameTransformStrategy().getAnnotationValue(),
                ENUMMAPPING_INCORRECT_TRANSFORMATION_STRATEGY,
                strategy,
                registeredStrategies
            );

            return false;
        }

        return true;
    }
}
