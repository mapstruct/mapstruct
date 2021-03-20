/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;
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
            return GetDateTimeFormatter.getDateTimeFormatterFieldName( conversionContext.getDateFormat() );
        }
        else {
            return ConversionUtils.dateTimeFormatter( conversionContext ) + "." + defaultFormatterSuffix();
        }
    }

    protected abstract String defaultFormatterSuffix();

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        // See http://docs.oracle.com/javase/tutorial/datetime/iso/format.html for how to parse Dates
        return new StringBuilder().append( conversionContext.getTargetType().createReferenceName() )
                                  .append( ".parse( " )
                                  .append( parametersListForParsing( conversionContext ) )
                                  .append( " )" ).toString();
    }

    private String parametersListForParsing(ConversionContext conversionContext) {
        // See http://docs.oracle.com/javase/tutorial/datetime/iso/format.html for how to format Dates
        StringBuilder parameterBuilder = new StringBuilder( "<SOURCE>" );
        if ( !Strings.isEmpty( conversionContext.getDateFormat() ) ) {
            parameterBuilder.append( ", " );
            parameterBuilder.append( dateTimeFormatter( conversionContext ) );
        }
        return parameterBuilder.toString();
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( DateTimeFormatter.class )
        );
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if ( !Strings.isEmpty( conversionContext.getDateFormat() ) ) {
            return Collections.asSet(
                conversionContext.getTargetType(),
                conversionContext.getTypeFactory().getType( DateTimeFormatter.class )
            );
        }

        return Collections.asSet( conversionContext.getTargetType() );
    }

    @Override
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        if ( Strings.isNotEmpty( conversionContext.getDateFormat() ) ) {
            return java.util.Collections.singletonList(
                new GetDateTimeFormatter( conversionContext.getTypeFactory(), conversionContext.getDateFormat() ) );
        }

        return super.getRequiredHelperMethods( conversionContext );
    }
}
