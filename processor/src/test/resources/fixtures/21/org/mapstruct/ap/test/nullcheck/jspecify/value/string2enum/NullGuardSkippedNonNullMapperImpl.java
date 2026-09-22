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
    date = "2026-09-13T18:54:02+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class NullGuardSkippedNonNullMapperImpl implements NullGuardSkippedNonNullMapper {

    @Override
    public @NonNull OrderType map(@NonNull String orderType) {

        OrderType orderType1 = switch ( orderType ) {
            case "SPECIAL" -> OrderType.EXTRA;
            case "DEFAULT" -> OrderType.STANDARD;
            case "RETAIL" -> OrderType.RETAIL;
            case "B2B" -> OrderType.B2B;
            case "EXTRA" -> OrderType.EXTRA;
            case "STANDARD" -> OrderType.STANDARD;
            case "NORMAL" -> OrderType.NORMAL;
            default -> OrderType.RETAIL;
        };

        return orderType1;
    }
}
