/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;

/**
 * Base class for a conversion between a type <tt>T</tt> and {@link String},
 * where {@code T#parse(String)} and {@code T#toString} are inverse operations.
 */
public abstract class AbstractStaticParseToStringConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toString()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return getParsingType().getCanonicalName() + ".parse( <SOURCE> )";
    }

    /**
     * @return the type which contains the <tt>parse(String)</tt> method
     */
    protected abstract Class<?> getParsingType();

}
