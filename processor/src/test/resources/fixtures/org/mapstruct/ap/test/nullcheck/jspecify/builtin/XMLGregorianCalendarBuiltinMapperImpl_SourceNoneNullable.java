/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.XMLGregorianCalendarBuiltinSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.XMLGregorianCalendarBuiltinTarget;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-18T00:00:00+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@NullMarked
public class XMLGregorianCalendarBuiltinMapperImpl implements XMLGregorianCalendarBuiltinMapper {

    private final DatatypeFactory datatypeFactory;

    public XMLGregorianCalendarBuiltinMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public XMLGregorianCalendarBuiltinTarget map(XMLGregorianCalendarBuiltinSource source) {

        XMLGregorianCalendarBuiltinTarget xMLGregorianCalendarBuiltinTarget = new XMLGregorianCalendarBuiltinTarget();

        xMLGregorianCalendarBuiltinTarget.setPublicStr( xmlGregorianCalendarToString( source.getPublicXml(), "dd.MM.yyyy" ) );
        xMLGregorianCalendarBuiltinTarget.setPublicXml( stringToXmlGregorianCalendar( source.getPublicStr(), "dd.MM.yyyy" ) );
        xMLGregorianCalendarBuiltinTarget.setStr( xmlGregorianCalendarToString( source.getXml(), "dd.MM.yyyy" ) );
        xMLGregorianCalendarBuiltinTarget.setXml( stringToXmlGregorianCalendar( source.getStr(), "dd.MM.yyyy" ) );

        return xMLGregorianCalendarBuiltinTarget;
    }

    private String xmlGregorianCalendarToString( XMLGregorianCalendar xcal, @Nullable String dateFormat ) {
        if (dateFormat == null ) {
            return xcal.toString();
        }
        else {
            Date d = xcal.toGregorianCalendar().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
            return sdf.format( d );
        }
    }

    private XMLGregorianCalendar stringToXmlGregorianCalendar( String date, @Nullable String dateFormat ) {
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
}
