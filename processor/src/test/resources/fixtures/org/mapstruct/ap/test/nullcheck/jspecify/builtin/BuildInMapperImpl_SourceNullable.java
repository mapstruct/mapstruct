/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.jspecify.annotations.NonNull;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.BuildInSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.BuildInTarget;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-14T23:12:34+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class BuildInMapperImpl implements BuildInMapper {

    private final DatatypeFactory datatypeFactory;

    public BuildInMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public BuildInTarget mapBuildIn(BuildInSource source) {
        if ( source == null ) {
            return null;
        }

        BuildInTarget buildInTarget = new BuildInTarget();

        if ( source.publicXmlGregCalToString != null ) {
            buildInTarget.publicXmlGregCalToString = xmlGregorianCalendarToString( source.publicXmlGregCalToString, "dd.MM.yyyy" );
        }
        if ( source.getXmlGregCalToString() != null ) {
            buildInTarget.setXmlGregCalToString( xmlGregorianCalendarToString( source.getXmlGregCalToString(), "dd.MM.yyyy" ) );
        }
        if ( source.publicStringToXmlGregCal != null ) {
            buildInTarget.publicStringToXmlGregCal = stringToXmlGregorianCalendar( source.publicStringToXmlGregCal, "dd.MM.yyyy" );
        }
        if ( source.getStringToXmlGregCal() != null ) {
            buildInTarget.setStringToXmlGregCal( stringToXmlGregorianCalendar( source.getStringToXmlGregCal(), "dd.MM.yyyy" ) );
        }
        if ( source.publicCalendarToString != null ) {
            buildInTarget.publicCalendarToString = xmlGregorianCalendarToString( calendarToXmlGregorianCalendar( source.publicCalendarToString ), "dd.MM.yyyy" );
        }
        if ( source.getCalendarToString() != null ) {
            buildInTarget.setCalendarToString( xmlGregorianCalendarToString( calendarToXmlGregorianCalendar( source.getCalendarToString() ), "dd.MM.yyyy" ) );
        }
        if ( source.publicStringToCalendar != null ) {
            buildInTarget.publicStringToCalendar = xmlGregorianCalendarToCalendar( stringToXmlGregorianCalendar( source.publicStringToCalendar, "dd.MM.yyyy" ) );
        }
        if ( source.getStringToCalendar() != null ) {
            buildInTarget.setStringToCalendar( xmlGregorianCalendarToCalendar( stringToXmlGregorianCalendar( source.getStringToCalendar(), "dd.MM.yyyy" ) ) );
        }
        if ( source.publicStringToXmlGregCal != null ) {
            buildInTarget.publicStringToXmlGregCalFormat = stringToXmlGregorianCalendar( source.publicStringToXmlGregCal, "dd.MM.yyyy" );
        }
        if ( source.getStringToXmlGregCal() != null ) {
            buildInTarget.setStringToXmlGregCalFormat( stringToXmlGregorianCalendar( source.getStringToXmlGregCal(), "dd.MM.yyyy" ) );
        }
        if ( source.publicStringToCalendar != null ) {
            buildInTarget.publicStringToCalendarFormat = xmlGregorianCalendarToCalendar( stringToXmlGregorianCalendar( source.publicStringToCalendar, "dd.MM.yyyy" ) );
        }
        if ( source.getStringToCalendar() != null ) {
            buildInTarget.setStringToCalendarFormat( xmlGregorianCalendarToCalendar( stringToXmlGregorianCalendar( source.getStringToCalendar(), "dd.MM.yyyy" ) ) );
        }
        if ( source.getCalendarToDate() != null ) {
            buildInTarget.setCalendarToDate( xmlGregorianCalendarToDate( calendarToXmlGregorianCalendar( source.getCalendarToDate() ) ) );
        }
        if ( source.getCalendarToXmlGregCal() != null ) {
            buildInTarget.setCalendarToXmlGregCal( calendarToXmlGregorianCalendar( source.getCalendarToXmlGregCal() ) );
        }
        if ( source.getCalendarToZonedDateTime() != null ) {
            buildInTarget.setCalendarToZonedDateTime( calendarToZonedDateTime( source.getCalendarToZonedDateTime() ) );
        }
        if ( source.getDateToCalendar() != null ) {
            buildInTarget.setDateToCalendar( xmlGregorianCalendarToCalendar( dateToXmlGregorianCalendar( source.getDateToCalendar() ) ) );
        }
        if ( source.getDateToXmlGregCal() != null ) {
            buildInTarget.setDateToXmlGregCal( dateToXmlGregorianCalendar( source.getDateToXmlGregCal() ) );
        }
        buildInTarget.setIterableSourceTarget( xMLGregorianCalendarListToStringList( source.getIterableSourceTarget() ) );
        if ( source.getXmlGregCalToCalendar() != null ) {
            buildInTarget.setXmlGregCalToCalendar( xmlGregorianCalendarToCalendar( source.getXmlGregCalToCalendar() ) );
        }
        if ( source.getXmlGregCalToDate() != null ) {
            buildInTarget.setXmlGregCalToDate( xmlGregorianCalendarToDate( source.getXmlGregCalToDate() ) );
        }
        if ( source.getZonedDateTimeToCalendar() != null ) {
            buildInTarget.setZonedDateTimeToCalendar( zonedDateTimeToCalendar( source.getZonedDateTimeToCalendar() ) );
        }
        if ( source.publicCalendarToDate != null ) {
            buildInTarget.publicCalendarToDate = xmlGregorianCalendarToDate( calendarToXmlGregorianCalendar( source.publicCalendarToDate ) );
        }
        if ( source.publicCalendarToXmlGregCal != null ) {
            buildInTarget.publicCalendarToXmlGregCal = calendarToXmlGregorianCalendar( source.publicCalendarToXmlGregCal );
        }
        if ( source.publicCalendarToZonedDateTime != null ) {
            buildInTarget.publicCalendarToZonedDateTime = calendarToZonedDateTime( source.publicCalendarToZonedDateTime );
        }
        if ( source.publicDateToCalendar != null ) {
            buildInTarget.publicDateToCalendar = xmlGregorianCalendarToCalendar( dateToXmlGregorianCalendar( source.publicDateToCalendar ) );
        }
        if ( source.publicDateToXmlGregCal != null ) {
            buildInTarget.publicDateToXmlGregCal = dateToXmlGregorianCalendar( source.publicDateToXmlGregCal );
        }
        buildInTarget.publicIterableSourceTarget = xMLGregorianCalendarListToStringList( source.publicIterableSourceTarget );
        if ( source.publicXmlGregCalToCalendar != null ) {
            buildInTarget.publicXmlGregCalToCalendar = xmlGregorianCalendarToCalendar( source.publicXmlGregCalToCalendar );
        }
        if ( source.publicXmlGregCalToDate != null ) {
            buildInTarget.publicXmlGregCalToDate = xmlGregorianCalendarToDate( source.publicXmlGregCalToDate );
        }
        if ( source.publicZonedDateTimeToCalendar != null ) {
            buildInTarget.publicZonedDateTimeToCalendar = zonedDateTimeToCalendar( source.publicZonedDateTimeToCalendar );
        }

        return buildInTarget;
    }

    private @NonNull String xmlGregorianCalendarToString( @NonNull XMLGregorianCalendar xcal, String dateFormat ) {
        if (dateFormat == null ) {
            return xcal.toString();
        }
        else {
            Date d = xcal.toGregorianCalendar().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
            return sdf.format( d );
        }
    }

    private @NonNull XMLGregorianCalendar stringToXmlGregorianCalendar( @NonNull String date, String dateFormat ) {
        try {
            if ( dateFormat != null ) {
                DateFormat df = new SimpleDateFormat( dateFormat );
                GregorianCalendar c = new GregorianCalendar();
                c.setTime( df.parse( date ) );
                return datatypeFactory.newXMLGregorianCalendar( c );
            }
            else {
                return datatypeFactory.newXMLGregorianCalendar( date );
            }
        }
        catch ( ParseException ex ) {
            throw new RuntimeException( ex );
        }
    }

    private @NonNull XMLGregorianCalendar calendarToXmlGregorianCalendar( @NonNull Calendar cal  ) {
        GregorianCalendar gcal = new GregorianCalendar( cal.getTimeZone() );
        gcal.setTimeInMillis( cal.getTimeInMillis() );
        return datatypeFactory.newXMLGregorianCalendar( gcal );
    }

    private @NonNull Calendar xmlGregorianCalendarToCalendar( @NonNull XMLGregorianCalendar xcal ) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis( xcal.toGregorianCalendar().getTimeInMillis() );
        return cal;
    }

    private static @NonNull Date xmlGregorianCalendarToDate( @NonNull XMLGregorianCalendar xcal ) {
        return xcal.toGregorianCalendar().getTime();
    }

    private @NonNull ZonedDateTime calendarToZonedDateTime(@NonNull Calendar cal) {
        return ZonedDateTime.ofInstant( cal.toInstant(), cal.getTimeZone().toZoneId() );
    }

    private @NonNull XMLGregorianCalendar dateToXmlGregorianCalendar( @NonNull Date date ) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }

    private @NonNull Calendar zonedDateTimeToCalendar(@NonNull ZonedDateTime dateTime) {
        Calendar instance = Calendar.getInstance( TimeZone.getTimeZone( dateTime.getZone() ) );
        instance.setTimeInMillis( dateTime.toInstant().toEpochMilli() );
        return instance;
    }

    protected @NonNull List<String> xMLGregorianCalendarListToStringList(List<XMLGregorianCalendar> list) {
        if ( list == null ) {
            return new ArrayList<>();
        }

        List<String> list1 = new ArrayList<>( list.size() );
        for ( XMLGregorianCalendar xMLGregorianCalendar : list ) {
            if ( xMLGregorianCalendar != null ) {
                list1.add( xmlGregorianCalendarToString( xMLGregorianCalendar, null ) );
            }
        }

        return list1;
    }
}
