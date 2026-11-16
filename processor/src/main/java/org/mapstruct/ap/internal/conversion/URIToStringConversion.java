/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.net.URI;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.uri;
import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Conversion between {@link java.net.URI} and {@link String}.
 *
 * @author Maciej Kucharczyk
 */
public class URIToStringConversion extends SimpleConversion {
    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toString()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return uri( conversionContext ) + ".create( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(final ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( URI.class ) );
    }
}
