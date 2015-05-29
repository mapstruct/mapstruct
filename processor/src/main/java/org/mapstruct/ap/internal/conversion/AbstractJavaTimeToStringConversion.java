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

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.JavaTimeConstants;
import org.mapstruct.ap.internal.util.Strings;

/**
 * <p>
 * Base type for mapping Java 8 time types to String and vice versa.
 * </p>
 * <p>
 * In general each type comes with a "parse" method to convert a string to this particular type.
 * For formatting a dedicated instance of {@link java.time.format.DateTimeFormatter} is used.
 * </p>
 * <p>
 * If no date format for mapping is specified predefined ISO* formatters from
 * {@link java.time.format.DateTimeFormatter} are used.
 * </p>
 * <p>
 * An overview of date and time types shipped with Java 8 can be found at
 * http://docs.oracle.com/javase/tutorial/datetime/iso/index.html.
 * </p>
 */
public abstract class AbstractJavaTimeToStringConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return dateTimeFormatter( conversionContext ) + ".format( <SOURCE> )";
    }

    private String dateTimeFormatter(ConversionContext conversionContext) {
        if ( !Strings.isEmpty( conversionContext.getDateFormat() ) ) {
            return "DateTimeFormatter.ofPattern( \"" + conversionContext.getDateFormat() + "\" )";
        }
        else {
            return "DateTimeFormatter." + defaultFormatterSuffix();
        }
    }

    protected abstract String defaultFormatterSuffix();

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        // See http://docs.oracle.com/javase/tutorial/datetime/iso/format.html for how to parse Dates
        return new StringBuilder().append( conversionContext.getTargetType().getFullyQualifiedName() )
                                  .append( ".parse( " )
                                  .append( parametersListForParsing( conversionContext ) )
                                  .append( " )" ).toString();
    }

    private String parametersListForParsing(ConversionContext conversionContext) {
        // See http://docs.oracle.com/javase/tutorial/datetime/iso/format.html for how to format Dates
        StringBuilder parameterBuilder = new StringBuilder( "<SOURCE>" );
        if ( !Strings.isEmpty( conversionContext.getDateFormat() ) ) {
            parameterBuilder.append( ", DateTimeFormatter.ofPattern( \"" )
                            .append( conversionContext.getDateFormat() )
                            .append( "\" )" );
        }
        return parameterBuilder.toString();
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
                        conversionContext.getTypeFactory().getType( JavaTimeConstants.DATE_TIME_FORMATTER_FQN )
        );
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
                        conversionContext.getTypeFactory().getType( JavaTimeConstants.DATE_TIME_FORMATTER_FQN )
        );
    }
}
