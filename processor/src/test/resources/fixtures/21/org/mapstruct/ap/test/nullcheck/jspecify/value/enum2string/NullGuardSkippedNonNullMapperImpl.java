/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.value.enum2string;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NonNull;
import org.mapstruct.ap.test.value.OrderType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-13T18:58:18+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class NullGuardSkippedNonNullMapperImpl implements NullGuardSkippedNonNullMapper {

    @Override
    public @NonNull String map(@NonNull OrderType orderType) {

        String string = switch ( orderType ) {
            case EXTRA -> "SPECIAL";
            case STANDARD -> "DEFAULT";
            case NORMAL -> "DEFAULT";
            case RETAIL -> "RETAIL";
            case B2B -> "B2B";
        };

        return string;
    }
}
