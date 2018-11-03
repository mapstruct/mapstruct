/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.util.Date;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Conversion between the following Joda types and {@link Date}:
 * <ul>
 * <li>org.joda.time.DateTime</li>
 * <li>org.joda.time.LocalDateTime</li>
 * <li>org.joda.time.LocalDate</li>
 * </ul>
 *
 * @author Timo Eckhardt
 */
public class JodaTimeToDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toDate()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "new " + conversionContext.getTargetType().createReferenceName() + "( <SOURCE> )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return asSet( conversionContext.getTargetType() );
    }
}
