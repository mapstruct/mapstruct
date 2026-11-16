/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Currency;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.currency;

/**
 * Conversion between {@link Currency} and {@link String}.
 *
 * @author Darren Rambaud
 */
public class CurrencyToStringConversion extends SimpleConversion {
    @Override
    protected String getToExpression(final ConversionContext conversionContext) {
        return "<SOURCE>.getCurrencyCode()";
    }

    @Override
    protected String getFromExpression(final ConversionContext conversionContext) {
        return currency( conversionContext ) + ".getInstance( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(final ConversionContext conversionContext) {
        return Collections.asSet( conversionContext.getTypeFactory().getType( Currency.class ) );
    }
}
