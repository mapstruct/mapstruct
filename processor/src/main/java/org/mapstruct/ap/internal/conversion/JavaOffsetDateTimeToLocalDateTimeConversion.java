/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.time.ZoneOffset;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.conversion.ConversionUtils.zoneOffset;

public class JavaOffsetDateTimeToLocalDateTimeConversion extends SimpleConversion {

    @Override
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toLocalDateTime()";
    }

    @Override
    protected String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>.atOffset( " + zoneOffset( conversionContext ) + ".UTC )";
    }

    @Override
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(
                conversionContext.getTypeFactory().getType( ZoneOffset.class )
        );
    }
}
