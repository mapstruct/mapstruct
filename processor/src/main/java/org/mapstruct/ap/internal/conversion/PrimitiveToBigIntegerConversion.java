/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import static org.mapstruct.ap.internal.util.Collections.asSet;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.bigInteger;

import java.math.BigInteger;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Conversion between {@link BigInteger} and native number types.
 *
 * @author Gunnar Morling
 */
public class PrimitiveToBigIntegerConversion extends SimpleConversion {

    private final Class<?> targetType;

    public PrimitiveToBigIntegerConversion(Class<?> targetType) {
        this.targetType = targetType;
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        String castString = "";
        if ( targetType == float.class || targetType == double.class ) {
            castString = "(long) ";
        }
        return bigInteger( conversionContext ) + ".valueOf( " + castString + "<SOURCE> )";
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
