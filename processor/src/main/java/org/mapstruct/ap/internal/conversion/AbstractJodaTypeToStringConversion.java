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

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Base class for conversions between Joda-Time types and String.
 *
 * @author Timo Eckhardt
 */
public abstract class AbstractJodaTypeToStringConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return conversionString( conversionContext, "print" ) + ".trim()";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        if ( conversionContext.getDateFormat() != null ) {
            return Collections.singleton(
                conversionContext.getTypeFactory()
                    .getType( JodaTimeConstants.DATE_TIME_FORMAT_FQN )
            );
        }
        else {
            return asSet(
                conversionContext.getTypeFactory().getType( JodaTimeConstants.DATE_TIME_FORMAT_FQN ),
                conversionContext.getTypeFactory().getType( Locale.class )
            );
        }
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return conversionString( conversionContext, parseMethod() );
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if ( conversionContext.getDateFormat() != null ) {
            return Collections.singleton(
                conversionContext.getTypeFactory()
                    .getType( JodaTimeConstants.DATE_TIME_FORMAT_FQN )
            );
        }
        else {
            return asSet(
                conversionContext.getTypeFactory().getType( JodaTimeConstants.DATE_TIME_FORMAT_FQN ),
                conversionContext.getTypeFactory().getType( Locale.class )
            );
        }
    }

    private String conversionString(ConversionContext conversionContext, String method) {
        StringBuilder conversionString = new StringBuilder( "DateTimeFormat" );
        conversionString.append( dateFormatPattern( conversionContext ) );
        conversionString.append( "." );
        conversionString.append( method );
        conversionString.append( "( <SOURCE> )" );
        return conversionString.toString();
    }

    private String dateFormatPattern(ConversionContext conversionContext) {
        StringBuilder conversionString = new StringBuilder();
        conversionString.append( ".forPattern(" );

        String dateFormat = conversionContext.getDateFormat();
        if ( dateFormat == null ) {
            conversionString.append( defaultDateFormatPattern() );

        }
        else {
            conversionString.append( " \"" );
            conversionString.append( dateFormat );
            conversionString.append( "\"" );

        }
        conversionString.append( " )" );
        return conversionString.toString();
    }

    private String defaultDateFormatPattern() {
        return " DateTimeFormat.patternForStyle( \"" + formatStyle() + "\", Locale.getDefault() )";
    }

    /**
     * @return the default format style to be applied if non is given explicitly.
     */
    protected abstract String formatStyle();

    /**
     * @return the name of the parse method for converting a String into a specific Joda-Time type.
     */
    protected abstract String parseMethod();
}
