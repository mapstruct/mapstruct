/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion;

import java.time.ZoneOffset;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-23T21:55:42+0900",
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
        if ( source.getZonedDateTimeAsInstant() != null ) {
            target.setZonedDateTimeAsInstant( source.getZonedDateTimeAsInstant().toInstant() );
        }
        if ( source.getOffsetDateTimeAsInstant() != null ) {
            target.setOffsetDateTimeAsInstant( source.getOffsetDateTimeAsInstant().toInstant() );
        }

        return target;
    }

    @Override
    public Source toSource(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getZonedDateTime() != null ) {
            source.setZonedDateTime( target.getZonedDateTime().atZone( ZoneOffset.UTC ) );
        }
        if ( target.getOffsetDateTime() != null ) {
            source.setOffsetDateTime( target.getOffsetDateTime().atOffset( ZoneOffset.UTC ) );
        }
        if ( target.getZonedDateTimeAsInstant() != null ) {
            source.setZonedDateTimeAsInstant( target.getZonedDateTimeAsInstant().atZone( ZoneOffset.UTC ) );
        }
        if ( target.getOffsetDateTimeAsInstant() != null ) {
            source.setOffsetDateTimeAsInstant( target.getOffsetDateTimeAsInstant().atOffset( ZoneOffset.UTC ) );
        }

        return source;
    }
}