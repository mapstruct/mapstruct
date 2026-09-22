/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtobuiltIn;

import java.time.ZonedDateTime;
import java.util.Calendar;
import javax.annotation.processing.Generated;
import org.jspecify.annotations.NonNull;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-14T22:18:00+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class MethodBuiltInMapperImpl implements MethodBuiltInMapper {

    @Override
    public MethodBuiltInTargetBean map(MethodBuiltInSourceBean source) {
        if ( source == null ) {
            return null;
        }

        MethodBuiltInTargetBean methodBuiltInTargetBean = new MethodBuiltInTargetBean();

        methodBuiltInTargetBean.setDateTime( toCalendarToZonedDateTime( source.getInput() ) );

        return methodBuiltInTargetBean;
    }

    private @NonNull ZonedDateTime calendarToZonedDateTime(@NonNull Calendar cal) {
        return ZonedDateTime.ofInstant( cal.toInstant(), cal.getTimeZone().toZoneId() );
    }

    private ZonedDateTime toCalendarToZonedDateTime(MethodBuiltInInput in) {
        Calendar methodBuiltInInput = toCalendar( in );
        if (methodBuiltInInput == null) {
            return null;
        }
        return calendarToZonedDateTime( methodBuiltInInput );
    }
}
