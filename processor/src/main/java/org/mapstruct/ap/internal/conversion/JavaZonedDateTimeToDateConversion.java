/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Date;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.date;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.zoneId;
import static org.mapstruct.ap.internal.conversion.ConversionUtils.zonedDateTime;
import static org.mapstruct.ap.internal.util.JavaTimeConstants.ZONED_DATE_TIME_FQN;
import static org.mapstruct.ap.internal.util.JavaTimeConstants.ZONE_ID_FQN;

/**
 * SimpleConversion for mapping {@link java.time.ZonedDateTime} to
 * {@link java.util.Date} and vice versa.
 */

public class JavaZonedDateTimeToDateConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return date( conversionContext ) + ".from( <SOURCE>.toInstant() )";
    }

    @Override
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( Date.class )
        );
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return zonedDateTime( conversionContext )
            + ".ofInstant( <SOURCE>.toInstant(), "
            + zoneId( conversionContext )
            + ".systemDefault() )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
            conversionContext.getTypeFactory().getType( ZONED_DATE_TIME_FQN ),
            conversionContext.getTypeFactory().getType( ZONE_ID_FQN )
        );
    }
}
