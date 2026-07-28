/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.custompatterndatetimeformattergenerated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:55:51+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (IBM Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" );
    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm_12071757710 = DateTimeFormatter.ofPattern( "dd.MM.yyyy HH.mm" );

    @Override
    public Target map(Source source) {
        if ( source == null ) {
            return null;
        }

        Target target = new Target();

        LocalDateTime localDateTime1 = source.getLocalDateTime1();
        if ( localDateTime1 != null ) {
            target.setLocalDateTime1( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( localDateTime1 ) );
        }
        LocalDateTime localDateTime2 = source.getLocalDateTime2();
        if ( localDateTime2 != null ) {
            target.setLocalDateTime2( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( localDateTime2 ) );
        }
        LocalDateTime localDateTime3 = source.getLocalDateTime3();
        if ( localDateTime3 != null ) {
            target.setLocalDateTime3( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071757710.format( localDateTime3 ) );
        }

        return target;
    }

    @Override
    public Source map(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        String localDateTime1 = target.getLocalDateTime1();
        if ( localDateTime1 != null ) {
            source.setLocalDateTime1( LocalDateTime.parse( localDateTime1, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        String localDateTime2 = target.getLocalDateTime2();
        if ( localDateTime2 != null ) {
            source.setLocalDateTime2( LocalDateTime.parse( localDateTime2, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        String localDateTime3 = target.getLocalDateTime3();
        if ( localDateTime3 != null ) {
            source.setLocalDateTime3( LocalDateTime.parse( localDateTime3, dateTimeFormatter_dd_MM_yyyy_HH_mm_12071757710 ) );
        }

        return source;
    }
}
