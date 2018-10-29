/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.bigInteger;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.math.BigInteger;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.NativeTypes;

/**
 * Conversion between {@link BigInteger} and wrappers of native number types.
 *
 * @author Gunnar Morling
 */
public class WrapperToBigIntegerConversion extends SimpleConversion {

    private final Class<?> targetType;

    public WrapperToBigIntegerConversion(Class<?> targetType) {
        this.targetType = NativeTypes.getPrimitiveType( targetType );
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        String toLongValueStr = "";
        if ( targetType == float.class || targetType == double.class ) {
            toLongValueStr = ".longValue()";
        }

        return bigInteger( conversionContext ) + ".valueOf( <SOURCE>" + toLongValueStr + " )";
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
       throw new IllegalStateException( "Not supported." );
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( BigInteger.class ) );
    }
}
