/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1576.java8;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T15:53:43+0200",
    comments = "version: , compiler: Eclipse JDT (Batch) 3.20.0.v20191203-2131, environment: Java 21.0.2 (Oracle Corporation)"
)
public class Issue1576MapperImpl implements Issue1576Mapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        Date localDateTime = source.getLocalDateTime();
        if ( localDateTime != null ) {
            target.setLocalDateTime( LocalDateTime.ofInstant( localDateTime.toInstant(), ZoneId.of( "UTC" ) ) );
        }
        Date localDate = source.getLocalDate();
        if ( localDate != null ) {
            target.setLocalDate( LocalDateTime.ofInstant( localDate.toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        String localTime = source.getLocalTime();
        if ( localTime != null ) {
            target.setLocalTime( LocalTime.parse( localTime ) );
        }
        Date zonedDateTime = source.getZonedDateTime();
        if ( zonedDateTime != null ) {
            target.setZonedDateTime( ZonedDateTime.ofInstant( zonedDateTime.toInstant(), ZoneId.systemDefault() ) );
        }
        Date instant = source.getInstant();
        if ( instant != null ) {
            target.setInstant( instant.toInstant() );
        }

        return target;
    }
}
