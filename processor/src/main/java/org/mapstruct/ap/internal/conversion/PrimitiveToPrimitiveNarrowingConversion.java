/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;

/**
 * Conversion between primitive types such as {@code byte} or {@code long}.
 *
 * @author Gunnar Morling
 */
public class PrimitiveToPrimitiveNarrowingConversion extends SimpleConversion {

    private final Class<?> targetType;

    public PrimitiveToPrimitiveNarrowingConversion(Class<?> targetType) {
        this.targetType = targetType;
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        return "(" + targetType + ") <SOURCE>";
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
       throw new IllegalStateException( "Not supported." );
    }

    @Override
    public boolean isNarrowing() {
        return true;
    }
}
