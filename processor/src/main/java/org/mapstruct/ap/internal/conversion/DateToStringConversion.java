/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.TypeConversion;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static java.util.Arrays.asList;
import static org.mapstruct.ap.internal.util.Collections.asSet;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.simpleDateFormat;

/**
 * Conversion between {@link String} and {@link Date}.
 *
 * @author Gunnar Morling
 */
public class DateToStringConversion implements ConversionProvider {

    @Override
    public Assignment to(ConversionContext conversionContext) {
        return new TypeConversion( asSet( conversionContext.getTypeFactory().getType( SimpleDateFormat.class ) ),
            Collections.<Type>emptyList(),
            getConversionExpression( conversionContext, "format" )
        );
    }

    @Override
    public Assignment from(ConversionContext conversionContext) {
        return new TypeConversion( asSet( conversionContext.getTypeFactory().getType( SimpleDateFormat.class ) ),
            asList( conversionContext.getTypeFactory().getType( ParseException.class ) ),
            getConversionExpression( conversionContext, "parse" )
        );
    }

    @Override
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        return Collections.emptyList();
    }

    private String getConversionExpression(ConversionContext conversionContext, String method) {
        StringBuilder conversionString = new StringBuilder( "new " );
        conversionString.append( simpleDateFormat( conversionContext ) );
        conversionString.append( '(' );

        if ( conversionContext.getDateFormat() != null ) {
            conversionString.append( " \"" );
            conversionString.append( conversionContext.getDateFormat() );
            conversionString.append( "\" " );
        }

        conversionString.append( ")." );
        conversionString.append( method );
        conversionString.append( "( <SOURCE> )" );

        return conversionString.toString();
    }
}
