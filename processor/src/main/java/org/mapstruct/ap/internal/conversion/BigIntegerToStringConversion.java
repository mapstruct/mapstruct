/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.asSet;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.bigDecimal;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.bigInteger;

/**
 * Conversion between {@link BigInteger} and {@link String}.
 *
 * @author Gunnar Morling
 */
public class BigIntegerToStringConversion extends AbstractNumberToStringConversion  {

    public BigIntegerToStringConversion() {
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
            sb.append( "( (" + bigDecimal( conversionContext ) + ") " );
            appendDecimalFormatter( sb, conversionContext );
            sb.append( ".parse( <SOURCE> )" );
            sb.append( " ).toBigInteger()" );
            return sb.toString();
        }
        else {
            return "new " + bigInteger( conversionContext ) + "( <SOURCE> )";
        }
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            return asSet( conversionContext.getTypeFactory().getType( BigDecimal.class ) );
        }
        else {
            return asSet( conversionContext.getTypeFactory().getType( BigInteger.class ) );
        }
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

        sb.append( " )" );
    }
}
