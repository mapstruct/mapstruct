/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;

public class JavaZonedDateTimeToLocalDateTimeConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toLocalDateTime()";

    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        throw new UnsupportedOperationException(
                "Mapping from LocalDateTime to ZonedDateTime is not supported - Zone information is required"
        );
    }
}
