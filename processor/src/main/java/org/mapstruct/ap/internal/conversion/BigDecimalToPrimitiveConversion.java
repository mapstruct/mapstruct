/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import org.mapstruct.ap.internal.model.common.ConversionContext;

/**
 * Conversion between {@link BigDecimal} and native number types.
 *
 * @author Gunnar Morling
 */
public class BigDecimalToPrimitiveConversion extends SimpleConversion {

    private final Class<?> targetType;

    public BigDecimalToPrimitiveConversion(Class<?> targetType) {
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
