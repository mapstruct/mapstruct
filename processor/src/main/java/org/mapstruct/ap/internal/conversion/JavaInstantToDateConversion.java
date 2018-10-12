/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import java.util.Date;
import java.util.Set;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.date;

/**
 * SimpleConversion for mapping {@link java.time.Instant} to
 * {@link Date} and vice versa.
 */
public class JavaInstantToDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return date( conversionContext ) + ".from( <SOURCE> )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet( conversionContext.getTypeFactory().getType( Date.class ) );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toInstant()";
    }
}
