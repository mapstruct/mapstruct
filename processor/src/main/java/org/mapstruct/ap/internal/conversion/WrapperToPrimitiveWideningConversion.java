/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;

/**
 * Conversion between primitive types such as {@code byte} and wrapper types
 * such as {@link Integer}.
 *
 * @author Gunnar Morling
 */
public class WrapperToPrimitiveWideningConversion extends SimpleConversion {

    private final Class<?> targetType;

    public WrapperToPrimitiveWideningConversion( Class<?> targetType ) {
        this.targetType = targetType;
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>." + targetType.getName() + "Value()";
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
       throw new IllegalStateException( "Not supported." );
    }
}
