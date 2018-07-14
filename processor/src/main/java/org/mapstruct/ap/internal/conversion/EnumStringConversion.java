/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Conversion between {@link String} and {@link Enum} types.
 *
 * @author Gunnar Morling
 */
public class EnumStringConversion extends SimpleConversion {

    @Override
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.name()";
    }

    @Override
    public String getFromExpression(ConversionContext conversionContext) {
        return "Enum.valueOf( " + conversionContext.getTargetType().getReferenceName()
            + ".class, <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet(
            conversionContext.getTargetType()
        );
    }
}
