/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.asSet;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.locale;

/**
 * Conversion between {@code DateTime} and {@link Calendar}.
 *
 * @author Timo Eckhardt
 */
public class JodaDateTimeToCalendarConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toCalendar( " + locale( conversionContext ) + ".getDefault() )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( Locale.class ) );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "new " + conversionContext.getTargetType().createReferenceName() + "( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet( conversionContext.getTargetType() );
    }
}
