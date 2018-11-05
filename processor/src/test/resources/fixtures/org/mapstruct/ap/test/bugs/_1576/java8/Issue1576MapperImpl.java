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
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-11-05T21:40:12+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class Issue1576MapperImpl implements Issue1576Mapper {

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        if ( source.getLocalDateTime() != null ) {
            target.setLocalDateTime( LocalDateTime.ofInstant( source.getLocalDateTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( source.getLocalDate() != null ) {
            target.setLocalDate( LocalDateTime.ofInstant( source.getLocalDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( source.getLocalTime() != null ) {
            target.setLocalTime( LocalTime.parse( source.getLocalTime() ) );
        }
        if ( source.getZonedDateTime() != null ) {
            target.setZonedDateTime( ZonedDateTime.ofInstant( source.getZonedDateTime().toInstant(), ZoneId.systemDefault() ) );
        }
        if ( source.getInstant() != null ) {
            target.setInstant( source.getInstant().toInstant() );
        }

        return target;
    }
}
