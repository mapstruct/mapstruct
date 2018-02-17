package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import java.util.Currency;
import java.util.Set;

/**
 * @author Darren Rambaud (2/16/18)
 * <p>
 * darren@rambaud.io
 */
public class CurrencyToStringConversion extends SimpleConversion {
    @Override
    protected String getToExpression(final ConversionContext conversionContext) {
        return "<SOURCE>.getCurrencyCode()";
    }

    @Override
    protected String getFromExpression(final ConversionContext conversionContext) {
        return "Currency.getInstance( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(final ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType( Currency.class ) );
    }
}
