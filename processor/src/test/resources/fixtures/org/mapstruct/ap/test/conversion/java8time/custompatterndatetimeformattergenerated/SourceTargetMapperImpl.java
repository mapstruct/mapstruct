/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.custompatterndatetimeformattergenerated;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-16T13:11:04+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_275 (AdoptOpenJDK)"
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

        if ( source.getLocalDateTime1() != null ) {
            target.setLocalDateTime1( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime1() ) );
        }
        if ( source.getLocalDateTime2() != null ) {
            target.setLocalDateTime2( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242.format( source.getLocalDateTime2() ) );
        }
        if ( source.getLocalDateTime3() != null ) {
            target.setLocalDateTime3( dateTimeFormatter_dd_MM_yyyy_HH_mm_12071757710.format( source.getLocalDateTime3() ) );
        }

        return target;
    }

    @Override
    public Source map(Target target) {
        if ( target == null ) {
            return null;
        }

        Source source = new Source();

        if ( target.getLocalDateTime1() != null ) {
            source.setLocalDateTime1( LocalDateTime.parse( target.getLocalDateTime1(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        if ( target.getLocalDateTime2() != null ) {
            source.setLocalDateTime2( LocalDateTime.parse( target.getLocalDateTime2(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071769242 ) );
        }
        if ( target.getLocalDateTime3() != null ) {
            source.setLocalDateTime3( LocalDateTime.parse( target.getLocalDateTime3(), dateTimeFormatter_dd_MM_yyyy_HH_mm_12071757710 ) );
        }

        return source;
    }
}
