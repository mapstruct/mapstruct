/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.url;
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
        return "new " + url( conversionContext ) + "( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(final ConversionContext conversionContext) {
        return asSet( conversionContext.getTypeFactory().getType( URL.class ) );
    }

    @Override
    protected List<Type> getFromConversionExceptionTypes(ConversionContext conversionContext) {
        return java.util.Collections.singletonList(
                conversionContext.getTypeFactory().getType( MalformedURLException.class )
        );
    }
}
