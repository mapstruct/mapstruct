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

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;

import java.util.Collections;
import java.util.Locale;

import static org.mapstruct.ap.util.Collections.asSet;

/**
 *
 */
public class JodaTypeToStringConversion implements ConversionProvider {

    private final Class sourceType;

    public JodaTypeToStringConversion(Class sourceType) {

        this.sourceType = sourceType;
    }

    public TypeConversion to(String sourceReference, ConversionContext conversionContext) {
        return new TypeConversion(
                        asSet(
                                        conversionContext.getTypeFactory().getType( DateTimeFormat.class ),
                                        conversionContext.getTypeFactory().getType( DateTime.class ) ),
                        Collections.<Type>emptyList(),
                        conversionString( sourceReference, conversionContext, "print" )
        );
    }

    public TypeConversion from(String targetReference, ConversionContext conversionContext) {
        return new TypeConversion(
                        asSet(
                                        conversionContext.getTypeFactory().getType( DateTimeFormat.class ),
                                        conversionContext.getTypeFactory().getType( DateTime.class ) ),
                        Collections.<Type>emptyList(),
                        conversionString(
                                        targetReference,
                                        conversionContext,
                                        parseMethodForTargetType( conversionContext ) )
        );
    }

    private String parseMethodForTargetType(ConversionContext conversionContext) {
        if ( DateTime.class.getName().equals( conversionContext.getTargetType().getFullyQualifiedName() ) ) {
            return "parseDateTime";
        }
        else if ( LocalDateTime.class.getName().equals( conversionContext.getTargetType().getFullyQualifiedName() ) ) {
            return "parseLocalDateTime";
        }
        else if ( LocalDate.class.getName().equals( conversionContext.getTargetType().getFullyQualifiedName() ) ) {
            return "parseLocalDate";
        }
        else if ( LocalTime.class.getName().equals( conversionContext.getTargetType().getFullyQualifiedName() ) ) {
            return "parseLocalTime";
        }
        else {
            throw new RuntimeException(
                            String.format(
                                            "Joda type %s not supported (yet)",
                                            conversionContext.getTargetType() )
            );
        }
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
        String dateFormat = conversionContext.getDateFormat();
        if ( isBlank( dateFormat ) ) {
            dateFormat = defaultDateFormatPattern( sourceType );
        }
        StringBuilder conversionString = new StringBuilder();
        conversionString.append( ".forPattern( \"" );
        conversionString.append( dateFormat );
        conversionString.append( "\" )" );
        return conversionString.toString();
    }

    public static String defaultDateFormatPattern(Class sourceType) {
        return DateTimeFormat.patternForStyle( formatStyle( sourceType ), Locale.getDefault() );
    }

    private static String formatStyle(Class sourceType) {
        if ( DateTime.class == sourceType ) {
            return "LL";
        }
        else if ( LocalDateTime.class == sourceType ) {
            return "LL";
        }
        else if ( LocalDate.class == sourceType ) {
            return "L-";
        }
        else if ( LocalTime.class == sourceType ) {
            return "-L";
        }
        else {
            throw new RuntimeException(
                            String.format(
                                            "Joda type %s not supported (yet)",
                                            sourceType )
            );
        }
    }

    // ugly. would be nice to have something like commons-lang or
    // guava on classpath. Or something homebrewn for
    // these kind of checks, assertions, etc.
    private boolean isBlank(String str) {
        return str == null || ( str != null && str.trim().equals( "" ) );
    }
}
