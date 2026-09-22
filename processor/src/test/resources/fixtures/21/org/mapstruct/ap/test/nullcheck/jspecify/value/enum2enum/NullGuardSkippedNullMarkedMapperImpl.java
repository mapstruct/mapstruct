/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.enum2enum;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-13T18:34:57+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@NullMarked
public class NullGuardSkippedNullMarkedMapperImpl implements NullGuardSkippedNullMarkedMapper {

    @Override
    public ExternalOrderType map(OrderType orderType) {

        ExternalOrderType externalOrderType = switch ( orderType ) {
            case EXTRA -> ExternalOrderType.SPECIAL;
            case STANDARD -> ExternalOrderType.DEFAULT;
            case NORMAL -> ExternalOrderType.DEFAULT;
            case RETAIL -> ExternalOrderType.RETAIL;
            case B2B -> ExternalOrderType.B2B;
        };

        return externalOrderType;
    }
}
