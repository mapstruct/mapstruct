/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.string2enum;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NonNull;
import org.mapstruct.ap.test.value.OrderType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-13T18:54:03+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class NullGuardSkippedNonNullMapperImpl implements NullGuardSkippedNonNullMapper {

    @Override
    public @NonNull OrderType map(@NonNull String orderType) {

        OrderType orderType1;

        switch ( orderType ) {
            case "SPECIAL": orderType1 = OrderType.EXTRA;
            break;
            case "DEFAULT": orderType1 = OrderType.STANDARD;
            break;
            case "RETAIL": orderType1 = OrderType.RETAIL;
            break;
            case "B2B": orderType1 = OrderType.B2B;
            break;
            case "EXTRA": orderType1 = OrderType.EXTRA;
            break;
            case "STANDARD": orderType1 = OrderType.STANDARD;
            break;
            case "NORMAL": orderType1 = OrderType.NORMAL;
            break;
            default: orderType1 = OrderType.RETAIL;
        }

        return orderType1;
    }
}
