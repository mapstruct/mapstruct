/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;

/**
 * Conversion between {@link Character} and {@link String}.
 *
 * @author Gunnar Morling
 */
public class CharWrapperToStringConversion extends SimpleConversion {

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toString()";
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>.charAt( 0 )";
    }
}
