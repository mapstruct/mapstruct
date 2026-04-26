/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.uri;
import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Conversion between {@link java.net.URL} and {@link String}.
 *
 * @author Adam Szatyin
 */
public class URLToStringConversion extends SimpleConversion {
    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toString()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return uri( conversionContext ) + ".create( <SOURCE> ).toURL()";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(final ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( URI.class ) );
    }

    @Override
    protected List<Type> getFromConversionExceptionTypes(ConversionContext conversionContext) {
        return java.util.Collections.singletonList(
                conversionContext.getTypeFactory().getType( MalformedURLException.class )
        );
    }
}
