/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.mapstruct.ap.internal.model.common.ConversionContext;

/**
 * SimpleConversion for mapping {@link LocalDateTime} to
 * {@link LocalDate} and vice versa.
 */

public class JavaLocalDateTimeToLocalDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toLocalDate()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>.atStartOfDay()";
    }

}
