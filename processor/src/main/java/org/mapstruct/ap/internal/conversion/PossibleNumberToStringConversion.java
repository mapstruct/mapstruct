/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.NativeTypes;


import org.mapstruct.ap.internal.util.Strings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Conversion hooks for {@link Number} to {@link String}. The idea being that <em>if</em>
 * we have a conversion to string and the source type is a {@code Number} and the mapping
 * has a {@link org.mapstruct.Mapping#numberFormat()} we will replace the standard conversion
 * with a {@link DecimalFormat} version
 *
 * @author Ciaran Liedeman
 */
public abstract class PossibleNumberToStringConversion extends SimpleConversion {

    protected final Class<?> primitiveType;
    protected final Class<?> wrapperType;
    private final Class<?> sourceType;
    private boolean sourceTypeNumberSubclass;

    public PossibleNumberToStringConversion(final Class<?> sourceType) {
        this.sourceType = sourceType;

        if ( sourceType.isPrimitive() ) {
            wrapperType = NativeTypes.getWrapperType( sourceType );
            primitiveType = sourceType;
        }
        else {
            wrapperType = sourceType;
            primitiveType = NativeTypes.getPrimitiveType( sourceType );
        }

        sourceTypeNumberSubclass = NativeTypes.isNumber( primitiveType )
                || NativeTypes.isNumber( sourceType );
    }

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        if ( isDecimalFormatConversion( conversionContext ) ) {
            return getFormatterToConversionExpression( conversionContext );
        }
        else {
            return getFallbackToExpression( conversionContext );
        }
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        if ( isDecimalFormatConversion( conversionContext ) ) {
            return getFormatterFromConversionExpression( conversionContext );
        }
        else {
            return getFallbackFromExpression( conversionContext );
        }
    }

    protected abstract String getFallbackToExpression(ConversionContext conversionContext);

    protected abstract String getFallbackFromExpression(ConversionContext conversionContext);


    public Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        if ( isDecimalFormatConversion( conversionContext ) ) {
            return Collections.singleton( conversionContext.getTypeFactory().getType( DecimalFormat.class ) );
        }
        else {
            return super.getToConversionImportTypes( conversionContext );
        }
    }

    private boolean isDecimalFormatConversion(ConversionContext conversionContext) {
        return sourceTypeNumberSubclass && conversionContext.getNumberFormat() != null;
    }

    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if ( isDecimalFormatConversion( conversionContext ) ) {
            return Collections.singleton( conversionContext.getTypeFactory().getType( DecimalFormat.class ) );
        }
        else {
            return super.getFromConversionImportTypes( conversionContext );
        }
    }

    protected List<Type> getFromConversionExceptionTypes(ConversionContext conversionContext) {
        if ( isDecimalFormatConversion( conversionContext ) ) {
            return Collections.singletonList( conversionContext.getTypeFactory().getType( ParseException.class ) );
        }
        else {
            return super.getFromConversionExceptionTypes( conversionContext );
        }
    }

    private String getFormatterToConversionExpression(ConversionContext conversionContext) {
        StringBuilder sb = new StringBuilder();

        appendDecimalFormatter( sb, conversionContext );
        sb.append( ".format" );
        sb.append( "( <SOURCE> )" );

        return sb.toString();
    }

    private String getFormatterFromConversionExpression(ConversionContext conversionContext) {
        StringBuilder sb = new StringBuilder();

        appendDecimalFormatter( sb, conversionContext );
        sb.append( ".parse" );
        sb.append( "( <SOURCE> ).toString()" );

        return getParseTypeExpression( sb.toString() );
    }

    private void appendDecimalFormatter(StringBuilder sb, ConversionContext conversionContext) {
        sb.append( "new DecimalFormat( " );

        if ( conversionContext.getNumberFormat() != null ) {
            sb.append( "\"" );
            sb.append( conversionContext.getNumberFormat() );
            sb.append( "\"" );
        }

        sb.append( " )" );
    }

    protected String getParseTypeExpression(String input) {
        if ( BigDecimal.class == sourceType ) {
            // TODO this step should be avoided, we return a BigDecimal from the formatter
            return "new BigDecimal(" + input + ")";
        }
        else if ( BigInteger.class == sourceType ) {
            return "new BigInteger(" + input + ")";
        }
        else {
            return wrapperType.getSimpleName() + ".parse" +
                    Strings.capitalize( primitiveType.getSimpleName() ) + "( " + input + " )";
        }
    }
}
