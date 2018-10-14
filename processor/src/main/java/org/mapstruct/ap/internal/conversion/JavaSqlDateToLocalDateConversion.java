/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.sql.Date;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.date;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.sqlDate;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.zoneOffset;
import static org.mapstruct.ap.internal.util.JavaTimeConstants.ZONE_OFFSET_FQN;

/**
 * SimpleConversion for mapping {@link Date} to
 * {@link java.time.LocalDate} and vice versa.
 */
public class JavaSqlDateToLocalDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toLocalDate()";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( ZONE_OFFSET_FQN )
        );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "new " + sqlDate( conversionContext ) + "(" +
            date( conversionContext )
            + ".from( <SOURCE>.atStartOfDay( "
            + zoneOffset( conversionContext )
            + ".UTC ).toInstant() ).getTime() )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( Date.class ),
            conversionContext.getTypeFactory().getType( java.util.Date.class ),
            conversionContext.getTypeFactory().getType( ZONE_OFFSET_FQN )
        );
    }

}
