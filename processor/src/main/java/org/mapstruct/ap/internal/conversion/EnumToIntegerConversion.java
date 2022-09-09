/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Conversion between {@link Enum} and {@link Integer} types.
 *
 * @author Jose Carlos Campanero Ortiz
 */
public class EnumToIntegerConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.ordinal()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return conversionContext.getTargetType().createReferenceName() + ".values()[ <SOURCE> ]";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet(
                conversionContext.getTargetType()
        );
    }
}
