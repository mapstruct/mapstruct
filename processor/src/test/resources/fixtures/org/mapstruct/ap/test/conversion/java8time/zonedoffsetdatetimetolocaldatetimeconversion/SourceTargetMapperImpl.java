/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-03T13:46:42+0900",
    comments = "version: , compiler: javac, environment: Java 25.0.2 (Homebrew)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getOffsetDateTime() != null ) {
            target.setOffsetDateTime( source.getOffsetDateTime().toLocalDateTime() );
        }
        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( source.getZonedDateTime().toLocalDateTime() );
        }

        return target;
    }
}