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
    date = "2026-09-13T18:58:19+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class NullGuardSkippedNonNullMapperImpl implements NullGuardSkippedNonNullMapper {

    @Override
    public @NonNull String map(@NonNull OrderType orderType) {

        String string;

        switch ( orderType ) {
            case EXTRA: string = "SPECIAL";
            break;
            case STANDARD: string = "DEFAULT";
            break;
            case NORMAL: string = "DEFAULT";
            break;
            case RETAIL: string = "RETAIL";
            break;
            case B2B: string = "B2B";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
        }

        return string;
    }
}
