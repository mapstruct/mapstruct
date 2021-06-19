/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JaxbConstants;
import org.mapstruct.ap.internal.util.JodaTimeConstants;
import org.mapstruct.ap.internal.util.XmlConstants;

/**
 * Registry for all built-in methods.
 *
 * @author Sjaak Derksen
 */
public class BuiltInMappingMethods {

    private final List<BuiltInMethod> builtInMethods;

    public BuiltInMappingMethods(TypeFactory typeFactory) {
        boolean isXmlGregorianCalendarPresent = isXmlGregorianCalendarAvailable( typeFactory );
        builtInMethods = new ArrayList<>( 20 );
        if ( isXmlGregorianCalendarPresent ) {
            builtInMethods.add( new DateToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToDate( typeFactory ) );
            builtInMethods.add( new StringToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToString( typeFactory ) );
            builtInMethods.add( new CalendarToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToCalendar( typeFactory ) );
            builtInMethods.add( new ZonedDateTimeToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToLocalDate( typeFactory ) );
            builtInMethods.add( new LocalDateToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new LocalDateTimeToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToLocalDateTime( typeFactory ) );
        }

        if ( isJaxbAvailable( typeFactory ) ) {
            builtInMethods.add( new JaxbElemToValue( typeFactory ) );
        }

        builtInMethods.add( new ZonedDateTimeToCalendar( typeFactory ) );
        builtInMethods.add( new CalendarToZonedDateTime( typeFactory ) );

        if ( isJodaTimeAvailable( typeFactory ) && isXmlGregorianCalendarPresent ) {
            builtInMethods.add( new JodaDateTimeToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToJodaDateTime( typeFactory ) );
            builtInMethods.add( new JodaLocalDateTimeToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToJodaLocalDateTime( typeFactory ) );
            builtInMethods.add( new JodaLocalDateToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToJodaLocalDate( typeFactory ) );
            builtInMethods.add( new JodaLocalTimeToXmlGregorianCalendar( typeFactory ) );
            builtInMethods.add( new XmlGregorianCalendarToJodaLocalTime( typeFactory ) );
        }
    }

    private static boolean isJaxbAvailable(TypeFactory typeFactory) {
        return typeFactory.isTypeAvailable( JaxbConstants.JAXB_ELEMENT_FQN );
    }

    private static boolean isXmlGregorianCalendarAvailable(TypeFactory typeFactory) {
        return typeFactory.isTypeAvailable( XmlConstants.JAVAX_XML_XML_GREGORIAN_CALENDAR );
    }

    private static boolean isJodaTimeAvailable(TypeFactory typeFactory) {
        return typeFactory.isTypeAvailable( JodaTimeConstants.DATE_TIME_FQN  );
    }

    public List<BuiltInMethod> getBuiltInMethods() {
        return builtInMethods;
    }
}
