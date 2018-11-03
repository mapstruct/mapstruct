/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.date;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.localDateTime;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.zoneOffset;

/**
 * SimpleConversion for mapping {@link java.time.LocalDate} to
 * {@link java.util.Date} and vice versa.
 */
public class JavaLocalDateToDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return date( conversionContext )
            + ".from( <SOURCE>.atStartOfDay( "
            + zoneOffset( conversionContext )
            + ".UTC ).toInstant() )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( Date.class ),
            conversionContext.getTypeFactory().getType( ZoneOffset.class )
        );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return localDateTime( conversionContext )
            + ".ofInstant( <SOURCE>.toInstant(), "
            + zoneOffset( conversionContext )
            + ".UTC ).toLocalDate()";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( LocalDateTime.class ),
            conversionContext.getTypeFactory().getType( ZoneOffset.class )
        );
    }
}
