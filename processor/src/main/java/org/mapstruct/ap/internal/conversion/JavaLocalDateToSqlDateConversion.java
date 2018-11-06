/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.sql.Date;
import java.time.ZoneOffset;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.sqlDate;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.zoneOffset;

/**
 * SimpleConversion for mapping {@link java.time.LocalDate} to
 * {@link Date} and vice versa.
 */
public class JavaLocalDateToSqlDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "new " + sqlDate( conversionContext ) + "( "
            + "<SOURCE>.atStartOfDay( "
            + zoneOffset( conversionContext )
            + ".UTC ).toInstant().toEpochMilli() )";
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
        return "<SOURCE>.toLocalDate()";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( ZoneOffset.class )
        );
    }

}
