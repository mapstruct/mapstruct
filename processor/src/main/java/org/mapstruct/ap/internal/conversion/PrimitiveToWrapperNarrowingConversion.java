/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.util.NativeTypes;

/**
 * Conversion between primitive types such as {@code byte} and wrapper types
 * such as {@link Integer}.
 *
 * @author Gunnar Morling
 */
public class PrimitiveToWrapperNarrowingConversion extends SimpleConversion {

    private final Class<?> targetType;

    public PrimitiveToWrapperNarrowingConversion( Class<?> targetType ) {
        this.targetType = NativeTypes.getPrimitiveType( targetType );
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        return "(" + targetType.getName() + ") <SOURCE>";
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
