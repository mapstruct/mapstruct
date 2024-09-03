/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.asSet;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.bigDecimal;

/**
 * Conversion between {@link BigDecimal} and {@link String}.
 *
 * @author Gunnar Morling
 */
public class BigDecimalToStringConversion extends AbstractNumberToStringConversion  {

    public BigDecimalToStringConversion() {
        super( true );
    }

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            StringBuilder sb = new StringBuilder();
            appendDecimalFormatter( sb, conversionContext );
            sb.append( ".format( <SOURCE> )" );
            return sb.toString();
        }
        else {
            return "<SOURCE>.toString()";
        }
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            StringBuilder sb = new StringBuilder();
            sb.append( "(" ).append( bigDecimal( conversionContext ) ).append( ") " );
            appendDecimalFormatter( sb, conversionContext );
            sb.append( ".parse( <SOURCE> )" );
            return sb.toString();
        }
        else {
            return "new " + bigDecimal( conversionContext ) + "( <SOURCE> )";
        }
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( BigDecimal.class ) );
    }

    @Override
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        List<HelperMethod> helpers = new ArrayList<>();
        if ( conversionContext.getNumberFormat() != null ) {
            helpers.add( new CreateDecimalFormat( conversionContext.getTypeFactory() ) );
        }
        return helpers;
    }

    private void appendDecimalFormatter(StringBuilder sb, ConversionContext conversionContext) {
        sb.append( "createDecimalFormat( " );
        if ( conversionContext.getNumberFormat() != null ) {
            sb.append( "\"" );
            sb.append( conversionContext.getNumberFormat() );
            sb.append( "\"" );
        }
        if ( conversionContext.getLocale() != null ) {
            sb.append( ", Locale.forLanguageTag( \"" );
            sb.append( conversionContext.getLocale() );
            sb.append( "\" )" );
        }
        else {
            sb.append( ", Locale.getDefault() " );
        }

        sb.append( " )" );
    }
}
