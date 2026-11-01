/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintobuiltIn;

import java.time.ZonedDateTime;
import java.util.Calendar;
import javax.annotation.processing.Generated;
import javax.xml.bind.JAXBElement;
import org.jspecify.annotations.NonNull;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-14T22:20:38+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class BuiltInToBuiltInMapperImpl implements BuiltInToBuiltInMapper {

    @Override
    public BuiltInToBuiltInTargetBean map(BuiltInToBuiltInSourceBean source) {
        if ( source == null ) {
            return null;
        }

        BuiltInToBuiltInTargetBean builtInToBuiltInTargetBean = new BuiltInToBuiltInTargetBean();

        builtInToBuiltInTargetBean.setDateTime( jaxbElemToValueToZonedDateTime( source.getElem() ) );

        return builtInToBuiltInTargetBean;
    }

    private @NonNull ZonedDateTime calendarToZonedDateTime(@NonNull Calendar cal) {
        return ZonedDateTime.ofInstant( cal.toInstant(), cal.getTimeZone().toZoneId() );
    }

    private <T> T jaxbElemToValue( JAXBElement<T> element ) {
        if ( element == null ) {
            return null;
        }

        return element.isNil() ? null : element.getValue();
    }

    private ZonedDateTime jaxbElemToValueToZonedDateTime(JAXBElement<Calendar> element) {
        Calendar jAXBElement = jaxbElemToValue( element );
        if (jAXBElement == null) {
            return null;
        }
        return calendarToZonedDateTime( jAXBElement );
    }
}
