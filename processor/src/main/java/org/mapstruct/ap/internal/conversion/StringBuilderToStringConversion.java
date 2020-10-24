/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;

/**
 * Handles conversion between a target type {@link StringBuilder} and {@link String}.
 *
 */
public class StringBuilderToStringConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toString()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "new " + ConversionUtils.stringBuilder( conversionContext ) + "( <SOURCE> )";
    }
}
