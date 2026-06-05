/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T15:54:14+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 21.0.2 (Oracle Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target toTarget(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        OffsetDateTime offsetDateTime = source.getOffsetDateTime();
        if ( offsetDateTime != null ) {
            target.setOffsetDateTime( offsetDateTime.toLocalDateTime() );
        }
        ZonedDateTime zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( zonedDateTime.toLocalDateTime() );
        }
        ZonedDateTime zonedDateTimeAsInstant = source.getZonedDateTimeAsInstant();
        if ( zonedDateTimeAsInstant != null ) {
            target.setZonedDateTimeAsInstant( zonedDateTimeAsInstant.toInstant() );
        }
        OffsetDateTime offsetDateTimeAsInstant = source.getOffsetDateTimeAsInstant();
        if ( offsetDateTimeAsInstant != null ) {
            target.setOffsetDateTimeAsInstant( offsetDateTimeAsInstant.toInstant() );
        }

        return target;
    }

    @Override
    public Source toSource(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        LocalDateTime zonedDateTime = target.getZonedDateTime();
        if ( zonedDateTime != null ) {
            source.setZonedDateTime( zonedDateTime.atZone( ZoneOffset.UTC ) );
        }
        LocalDateTime offsetDateTime = target.getOffsetDateTime();
        if ( offsetDateTime != null ) {
            source.setOffsetDateTime( offsetDateTime.atOffset( ZoneOffset.UTC ) );
        }
        Instant zonedDateTimeAsInstant = target.getZonedDateTimeAsInstant();
        if ( zonedDateTimeAsInstant != null ) {
            source.setZonedDateTimeAsInstant( zonedDateTimeAsInstant.atZone( ZoneOffset.UTC ) );
        }
        Instant offsetDateTimeAsInstant = target.getOffsetDateTimeAsInstant();
        if ( offsetDateTimeAsInstant != null ) {
            source.setOffsetDateTimeAsInstant( offsetDateTimeAsInstant.atOffset( ZoneOffset.UTC ) );
        }

        return source;
    }
}
