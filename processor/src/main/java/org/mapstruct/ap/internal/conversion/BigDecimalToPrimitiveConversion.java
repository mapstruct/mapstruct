/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.asSet;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.bigDecimal;

/**
 * Conversion between {@link BigDecimal} and native number types.
 *
 * @author Gunnar Morling
 */
public class BigDecimalToPrimitiveConversion extends SimpleConversion {

    private final Class<?> targetType;

    public BigDecimalToPrimitiveConversion(Class<?> targetType) {
        if ( !targetType.isPrimitive() ) {
            throw new IllegalArgumentException( targetType + " is no primitive type." );
        }

        this.targetType = targetType;
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>." + targetType.getName() + "Value()";
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        return bigDecimal( conversionContext ) + ".valueOf( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( BigDecimal.class ) );
    }
}
