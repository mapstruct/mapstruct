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

import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.util.Strings;

import java.util.Collections;
import java.util.Locale;

import static org.mapstruct.ap.util.Collections.asSet;

/**
 *
 */
public abstract class AbstractJodaTypeToStringConversion implements ConversionProvider {

    public TypeConversion to(String sourceReference, ConversionContext conversionContext) {
        return new TypeConversion(
                        asSet(
                                        conversionContext.getTypeFactory().getType( dateTimeFormatClass() ),
                                        conversionContext.getTypeFactory().getType( Locale.class ) ),
                        Collections.<Type>emptyList(),
                        conversionString( sourceReference, conversionContext, "print" )
        );
    }

    public TypeConversion from(String targetReference, ConversionContext conversionContext) {
        return new TypeConversion(
                        asSet(
                                        conversionContext.getTypeFactory().getType( dateTimeFormatClass() ) ),
                        Collections.<Type>emptyList(),
                        conversionString(
                                        targetReference,
                                        conversionContext,
                                        parseMethod() )
        );
    }

    private String conversionString(String reference, ConversionContext conversionContext, String method) {
        StringBuilder conversionString = new StringBuilder( "DateTimeFormat" );
        conversionString.append( dateFormatPattern( conversionContext ) );
        conversionString.append( "." );
        conversionString.append( method );
        conversionString.append( "( " );
        conversionString.append( reference );
        conversionString.append( " )" );
        return conversionString.toString();
    }

    private String dateFormatPattern(ConversionContext conversionContext) {
        StringBuilder conversionString = new StringBuilder();
        conversionString.append( ".forPattern(" );

        String dateFormat = conversionContext.getDateFormat();
        if ( Strings.isEmpty( dateFormat ) ) {
            conversionString.append( defaultDateFormatPattern() );

        }
        else {
            conversionString.append( " \"" );
            conversionString.append( dateFormat );
            conversionString.append( "\"" );

        }
        conversionString.append( ")" );
        return conversionString.toString();
    }

    public String defaultDateFormatPattern() {
        return "DateTimeFormat.patternForStyle( \"" + formatStyle() + "\", Locale.getDefault() )";
    }

    protected abstract String formatStyle();

    protected abstract String parseMethod();

    private Class dateTimeFormatClass() {
        try {
            return Class.forName( "org.joda.time.format.DateTimeFormat" );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( "org.joda.time.format.DateTimeFormat  not found on classpath" );
        }
    }

}
