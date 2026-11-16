/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4088.jdk17;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-15T08:29:21+0000",
    comments = "version: , compiler: javac, environment: Java 21.0.11 (Red Hat, Inc.)"
)
public class Issue4088MapperImpl implements Issue4088Mapper {

    @Override
    public Target create(Issue4088Mapper.Request request) {
        if ( request == null ) {
            return null;
        }

        Target target = new Target();

        target.setDate( sourceDateTimeToTargetDate( requestAppointmentDateTime( request ) ) );

        return target;
    }

    @Override
    public void update(Issue4088Mapper.Request request, Target target) {
        if ( request == null ) {
            return;
        }

        SourceDateTime dateTime = requestAppointmentDateTime( request );
        if ( dateTime != null ) {
            if ( target.getDate() == null ) {
                target.setDate( new TargetDate() );
            }
            sourceDateTimeToTargetDate1( dateTime, target.getDate() );
        }
        else {
            target.setDate( null );
        }
    }

    private SourceDateTime requestAppointmentDateTime(Issue4088Mapper.Request request) {
        Issue4088Mapper.Request.Appointment appointment = request.appointment();
        if ( appointment == null ) {
            return null;
        }
        return appointment.dateTime();
    }

    protected TargetDate sourceDateTimeToTargetDate(SourceDateTime sourceDateTime) {
        if ( sourceDateTime == null ) {
            return null;
        }

        TargetDate targetDate = new TargetDate();

        targetDate.setDay( sourceDateTime.getDay() );
        targetDate.setMonth( sourceDateTime.getMonth() );
        targetDate.setYear( sourceDateTime.getYear() );

        return targetDate;
    }

    protected void sourceDateTimeToTargetDate1(SourceDateTime sourceDateTime, TargetDate mappingTarget) {
        if ( sourceDateTime == null ) {
            return;
        }

        mappingTarget.setDay( sourceDateTime.getDay() );
        mappingTarget.setMonth( sourceDateTime.getMonth() );
        mappingTarget.setYear( sourceDateTime.getYear() );
    }
}
