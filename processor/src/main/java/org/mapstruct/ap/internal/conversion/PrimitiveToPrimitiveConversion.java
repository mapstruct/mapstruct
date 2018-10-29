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
public class PrimitiveToPrimitiveConversion extends SimpleConversion {

    private final Class<?> sourceType;

    public PrimitiveToPrimitiveConversion(Class<?> sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>";
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        return "(" + sourceType + ") <SOURCE>";
    }

}
