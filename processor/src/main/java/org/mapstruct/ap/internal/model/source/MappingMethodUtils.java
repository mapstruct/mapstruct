/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

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
     * source parameter and result type are enum types.
     *
     * @param method to check
     *
     * @return {@code true} if the method is for enum mapping, {@code false} otherwise
     */
    public static boolean isEnumMapping(Method method) {
        return method.getSourceParameters().size() == 1
            && first( method.getSourceParameters() ).getType().isEnumType()
            && method.getResultType().isEnumType();
    }
}
