/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.decimalFormat;

/**
 * Conversion between wrapper types such as {@link Integer} and {@link String}.
 *
 * @author Gunnar Morling
 */
public class WrapperToStringConversion extends AbstractNumberToStringConversion  {

    private final Class<?> sourceType;
    private final Class<?> primitiveType;

    public WrapperToStringConversion(Class<?> sourceType) {
        super( NativeTypes.isNumber( sourceType ) );
        if ( sourceType.isPrimitive() ) {
            throw new IllegalArgumentException( sourceType + " is no wrapper type." );
        }

        this.sourceType = sourceType;
        this.primitiveType = NativeTypes.getPrimitiveType( sourceType );
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
            return "String.valueOf( <SOURCE> )";
        }
    }

    @Override
    public Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            return Collections.singleton(
                conversionContext.getTypeFactory().getType( DecimalFormat.class )
            );
        }

        return Collections.emptySet();
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            StringBuilder sb = new StringBuilder();
            appendDecimalFormatter( sb, conversionContext );
            sb.append( ".parse( <SOURCE> )." );
            sb.append( primitiveType.getSimpleName() );
            sb.append( "Value()" );
            return sb.toString();
        }
        else {
            return sourceType.getSimpleName() + ".parse"
                + Strings.capitalize( primitiveType.getSimpleName() ) + "( <SOURCE> )";
        }
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if ( requiresDecimalFormat( conversionContext ) ) {
            return Collections.singleton(
                conversionContext.getTypeFactory().getType( DecimalFormat.class )
            );
        }

        return Collections.emptySet();
    }

    private void appendDecimalFormatter(StringBuilder sb, ConversionContext conversionContext) {
        sb.append( "new " );
        sb.append( decimalFormat( conversionContext ) );
        sb.append( "( " );

        if ( conversionContext.getNumberFormat() != null ) {
            sb.append( "\"" );
            sb.append( conversionContext.getNumberFormat() );
            sb.append( "\"" );
        }

        sb.append( " )" );
    }
}
