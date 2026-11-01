/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.jspecify.annotations.NonNull;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JodaTimeBuildInSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.JodaTimeBuildInTarget;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-15T23:37:23+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class JodaTimeBuildInMapperImpl implements JodaTimeBuildInMapper {

    private final DatatypeFactory datatypeFactory;

    public JodaTimeBuildInMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public JodaTimeBuildInTarget mapBuildIn(JodaTimeBuildInSource source) {
        if ( source == null ) {
            return null;
        }

        JodaTimeBuildInTarget jodaTimeBuildInTarget = new JodaTimeBuildInTarget();

        jodaTimeBuildInTarget.setDateTimeToXmlGregorianCalendar( jodaDateTimeToXmlGregorianCalendar( source.getDateTimeToXmlGregorianCalendar() ) );
        jodaTimeBuildInTarget.setLocalDateTimeToXmlGregorianCalendar( jodaLocalDateTimeToXmlGregorianCalendar( source.getLocalDateTimeToXmlGregorianCalendar() ) );
        jodaTimeBuildInTarget.setLocalDateToXmlGregorianCalendar( jodaLocalDateToXmlGregorianCalendar( source.getLocalDateToXmlGregorianCalendar() ) );
        jodaTimeBuildInTarget.setLocalTimeToXmlGregorianCalendar( jodaLocalTimeToXmlGregorianCalendar( source.getLocalTimeToXmlGregorianCalendar() ) );
        jodaTimeBuildInTarget.setXmlGregorianCalendarToDateTime( xmlGregorianCalendarToJodaDateTime( source.getXmlGregorianCalendarToDateTime() ) );
        jodaTimeBuildInTarget.setXmlGregorianCalendarToLocalDate( xmlGregorianCalendarToJodaLocalDate( source.getXmlGregorianCalendarToLocalDate() ) );
        jodaTimeBuildInTarget.setXmlGregorianCalendarToLocalDateTime( xmlGregorianCalendarToJodaLocalDateTime( source.getXmlGregorianCalendarToLocalDateTime() ) );
        jodaTimeBuildInTarget.setXmlGregorianCalendarToLocalTime( xmlGregorianCalendarToJodaLocalTime( source.getXmlGregorianCalendarToLocalTime() ) );
        jodaTimeBuildInTarget.publicDateTimeToXmlGregorianCalendar = jodaDateTimeToXmlGregorianCalendar( source.publicDateTimeToXmlGregorianCalendar );
        jodaTimeBuildInTarget.publicLocalDateTimeToXmlGregorianCalendar = jodaLocalDateTimeToXmlGregorianCalendar( source.publicLocalDateTimeToXmlGregorianCalendar );
        jodaTimeBuildInTarget.publicLocalDateToXmlGregorianCalendar = jodaLocalDateToXmlGregorianCalendar( source.publicLocalDateToXmlGregorianCalendar );
        jodaTimeBuildInTarget.publicLocalTimeToXmlGregorianCalendar = jodaLocalTimeToXmlGregorianCalendar( source.publicLocalTimeToXmlGregorianCalendar );
        jodaTimeBuildInTarget.publicXmlGregorianCalendarToDateTime = xmlGregorianCalendarToJodaDateTime( source.publicXmlGregorianCalendarToDateTime );
        jodaTimeBuildInTarget.publicXmlGregorianCalendarToLocalDate = xmlGregorianCalendarToJodaLocalDate( source.publicXmlGregorianCalendarToLocalDate );
        jodaTimeBuildInTarget.publicXmlGregorianCalendarToLocalDateTime = xmlGregorianCalendarToJodaLocalDateTime( source.publicXmlGregorianCalendarToLocalDateTime );
        jodaTimeBuildInTarget.publicXmlGregorianCalendarToLocalTime = xmlGregorianCalendarToJodaLocalTime( source.publicXmlGregorianCalendarToLocalTime );

        return jodaTimeBuildInTarget;
    }

    private @NonNull XMLGregorianCalendar jodaDateTimeToXmlGregorianCalendar( @NonNull DateTime dt ) {
        return datatypeFactory.newXMLGregorianCalendar(
            dt.getYear(),
            dt.getMonthOfYear(),
            dt.getDayOfMonth(),
            dt.getHourOfDay(),
            dt.getMinuteOfHour(),
            dt.getSecondOfMinute(),
            dt.getMillisOfSecond(),
            dt.getZone().getOffset( null ) / 60000 );
    }

    private @NonNull XMLGregorianCalendar jodaLocalDateTimeToXmlGregorianCalendar( @NonNull LocalDateTime dt ) {
        return  datatypeFactory.newXMLGregorianCalendar(
            dt.getYear(),
            dt.getMonthOfYear(),
            dt.getDayOfMonth(),
            dt.getHourOfDay(),
            dt.getMinuteOfHour(),
            dt.getSecondOfMinute(),
            dt.getMillisOfSecond(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private @NonNull XMLGregorianCalendar jodaLocalDateToXmlGregorianCalendar( @NonNull LocalDate dt ) {
        return datatypeFactory.newXMLGregorianCalendarDate(
            dt.getYear(),
            dt.getMonthOfYear(),
            dt.getDayOfMonth(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private @NonNull XMLGregorianCalendar jodaLocalTimeToXmlGregorianCalendar( @NonNull LocalTime dt ) {
        return datatypeFactory.newXMLGregorianCalendarTime(
            dt.getHourOfDay(),
            dt.getMinuteOfHour(),
            dt.getSecondOfMinute(),
            dt.getMillisOfSecond(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static DateTime xmlGregorianCalendarToJodaDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getTimezone() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond(),
                    DateTimeZone.forOffsetMillis( xcal.getTimezone() * 60000 )
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getTimezone() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    DateTimeZone.forOffsetMillis( xcal.getTimezone() * 60000 )
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else if ( xcal.getTimezone() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    DateTimeZone.forOffsetMillis( xcal.getTimezone() * 60000 )
                );
            }
            else {
                return new DateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    private static LocalDate xmlGregorianCalendarToJodaLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED )  {
            return new LocalDate( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
        }

        return null;
    }

    private static LocalDateTime xmlGregorianCalendarToJodaLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new LocalDateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new LocalDateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return new LocalDateTime( xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    private static LocalTime xmlGregorianCalendarToJodaLocalTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new LocalTime( xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    xcal.getMillisecond()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return new LocalTime(
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return new LocalTime( xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }
}
