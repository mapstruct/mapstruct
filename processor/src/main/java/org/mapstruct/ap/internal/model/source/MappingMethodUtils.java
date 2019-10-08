/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import org.mapstruct.ap.internal.prism.ValueMappingPrism;
import org.mapstruct.ap.internal.prism.ValueMappingsPrism;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * @author Filip Hrisafov
 */
public final class MappingMethodUtils {

    /**
     * Hide default constructor.
     */
    private MappingMethodUtils() {
    }

    /**
     * Checks if the provided {@code method} is for enum mapping. A Method is an Enum Mapping method when the
     * <ol>
     * <li>source parameter type and result type are enum types</li>
     * <li>source parameter type is a String and result type is an enum type</li>
     * <li>source parameter type is a enum type and result type is a String</li>
     * </ol>
     *
     * @param method to check
     *
     * @return {@code true} if the method is for enum mapping, {@code false} otherwise
     */
    public static boolean isEnumMapping(Method method) {
        if ( method.getSourceParameters().size() != 1 ) {
            return false;
        }

        Type source = first( method.getSourceParameters() ).getType();
        Type result = method.getResultType();
        if ( source.isEnumType() && result.isEnumType() ) {
            return isProvenByAnnotation( method );
        }
        if ( source.isString() && result.isEnumType() ) {
            return isProvenByAnnotation( method );
        }
        if ( source.isEnumType()  && result.isString() ) {
            return isProvenByAnnotation( method );
        }
        return false;
    }

    /**
     * See #1788 for example
     *
     * @param method the method
     * @return true if method is proved by annotation @ValueMapping(s) or disproved by @BeanMapping
     * also true if cannot be confirmed by any annotation.
     */
    private static boolean isProvenByAnnotation(Method method ) {
        if ( ValueMappingPrism.getInstanceOn( method.getExecutable() ) != null  ||
            ValueMappingsPrism.getInstanceOn( method.getExecutable() ) != null ) {
            return true;
        }
        return BeanMappingPrism.getInstanceOn( method.getExecutable() ) == null;
    }
}
