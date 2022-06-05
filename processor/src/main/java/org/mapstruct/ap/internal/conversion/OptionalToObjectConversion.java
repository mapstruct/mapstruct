/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Optional;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/**
 * Conversion between {@link Optional} and {@link Object}.
 *
 * @author Iaroslav Bogdanchikov
 */
public class OptionalToObjectConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.get()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "Optional.ofNullable( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(final ConversionContext conversionContext) {
        return Collections.asSet( conversionContext.getTypeFactory().getType( Optional.class ) );
    }
}
