/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;

import static org.mapstruct.ap.util.Collections.asSet;

/**
 * Conversion between {@link String} and {@link Date}.
 *
 * @author Gunnar Morling
 */
public class DateToStringConversion implements ConversionProvider {

    @Override
    public TypeConversion to(String sourceReference, ConversionContext conversionContext) {
        return new TypeConversion(
            asSet( conversionContext.getTypeFactory().getType( SimpleDateFormat.class ) ),
            Collections.<Type>emptyList(),
            getOpenExpression( conversionContext, "format" ),
            sourceReference,
            getCloseExpression()
        );
    }

    @Override
    public TypeConversion from(String targetReference, ConversionContext conversionContext) {
        return new TypeConversion(
            asSet( conversionContext.getTypeFactory().getType( SimpleDateFormat.class ) ),
            Arrays.asList( conversionContext.getTypeFactory().getType( ParseException.class ) ),
            getOpenExpression( conversionContext, "parse" ),
            targetReference,
            getCloseExpression()
        );
    }

    private String getOpenExpression(ConversionContext conversionContext, String method) {
        StringBuilder conversionString = new StringBuilder( "new SimpleDateFormat(" );

        if ( conversionContext.getDateFormat() != null ) {
            conversionString.append( " \"" );
            conversionString.append( conversionContext.getDateFormat() );
            conversionString.append( "\" " );
        }

        conversionString.append( ")." );
        conversionString.append( method );
        conversionString.append( "( " );

        return conversionString.toString();
    }

    private String getCloseExpression() {
       return " )";
    }
}
