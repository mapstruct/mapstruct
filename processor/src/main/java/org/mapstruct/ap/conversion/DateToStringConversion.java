/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.TypeConversion;

import static org.mapstruct.ap.util.Collections.asSet;

/**
 * Conversion between {@link String} and {@link Date}.
 *
 * @author Gunnar Morling
 */
public class DateToStringConversion implements ConversionProvider {

    @Override
    public TypeConversion to(String sourceReference, Context conversionContext) {
        return new TypeConversion(
            asSet( Type.forClass( SimpleDateFormat.class ) ),
            Collections.<Type>emptyList(),
            getConversionString( sourceReference, conversionContext, "format" )
        );
    }

    @Override
    public TypeConversion from(String targetReference, Context conversionContext) {
        return new TypeConversion(
            asSet( Type.forClass( SimpleDateFormat.class ) ),
            Arrays.asList( Type.forClass( ParseException.class ) ),
            getConversionString( targetReference, conversionContext, "parse" )
        );
    }

    private String getConversionString(String targetReference, Context conversionContext, String method) {
        StringBuilder conversionString = new StringBuilder( "new SimpleDateFormat(" );

        if ( conversionContext.getDateFormat() != null ) {
            conversionString.append( " \"" );
            conversionString.append( conversionContext.getDateFormat() );
            conversionString.append( "\" " );
        }

        conversionString.append( ")." );
        conversionString.append( method );
        conversionString.append( "( " );
        conversionString.append( targetReference );
        conversionString.append( " )" );

        return conversionString.toString();
    }
}
