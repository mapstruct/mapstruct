/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the nested invocation of mapping methods.
 *
 * @author Sjaak Derksen
 */
@IssueKey("134")
public class NestedMappingMethodInvocationTest {

    public static final QName QNAME = new QName( "dont-care" );

    private Locale originalLocale;

    @BeforeEach
    public void setDefaultLocale() {
        originalLocale = Locale.getDefault();
        Locale.setDefault( Locale.GERMAN );
    }

    @AfterEach
    public void tearDown() {
        Locale.setDefault( originalLocale );
    }

    @ProcessorTest
    @WithClasses( {
        OrderTypeToOrderDtoMapper.class,
        OrderDto.class,
        OrderDetailsDto.class,
        OrderDetailsType.class,
        OrderType.class
    } )
    public void shouldMapViaMethodAndMethod() throws DatatypeConfigurationException {
        OrderTypeToOrderDtoMapper instance = OrderTypeToOrderDtoMapper.INSTANCE;
        OrderDto target = instance.sourceToTarget( createOrderType() );

        assertThat( target ).isNotNull();
        assertThat( target.getOrderNumber() ).isEqualTo( 5L );
        assertThat( target.getDates() ).containsExactly( "02.03.1999", "28.07.2004" );

        assertThat( target.getOrderDetails() ).isNotNull();
        assertThat( target.getOrderDetails().getName() ).isEqualTo( "test" );
        assertThat( target.getOrderDetails().getDescription() ).containsExactly( "elem1", "elem2" );
    }

    @ProcessorTest
    @WithClasses( {
        SourceTypeTargetDtoMapper.class,
        SourceType.class,
        ObjectFactory.class,
        TargetDto.class
    } )
    public void shouldMapViaMethodAndConversion() {
        SourceTypeTargetDtoMapper instance = SourceTypeTargetDtoMapper.INSTANCE;

        TargetDto target = instance.sourceToTarget( createSource() );

        assertThat( target ).isNotNull();
        assertThat( target.getDate() ).isEqualTo( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
    }

    @ProcessorTest
    @WithClasses( {
        SourceTypeTargetDtoMapper.class,
        SourceType.class,
        ObjectFactory.class,
        TargetDto.class
    } )
    public void shouldMapViaConversionAndMethod() {
        SourceTypeTargetDtoMapper instance = SourceTypeTargetDtoMapper.INSTANCE;

        SourceType source = instance.targetToSource( createTarget() );

        assertThat( source ).isNotNull();
        assertThat( source.getDate().getValue() ).isEqualTo( "06.07.2013" );
        assertThat( source.getDate().getName() ).isEqualTo( QNAME );
    }

    private OrderType createOrderType() throws DatatypeConfigurationException {
        List<JAXBElement<XMLGregorianCalendar>> dates = new ArrayList<>();
        dates.add(
            new JAXBElement<>(
                QNAME,
                XMLGregorianCalendar.class,
                createXmlCal( 1999, 3, 2 )
            )
        );
        dates.add(
            new JAXBElement<>(
                QNAME,
                XMLGregorianCalendar.class,
                createXmlCal( 2004, 7, 28 )
            )
        );

        List<JAXBElement<String>> description = new ArrayList<>();
        description.add( new JAXBElement<>( QNAME, String.class, "elem1" ) );
        description.add( new JAXBElement<>( QNAME, String.class, "elem2" ) );

        OrderType orderType = new OrderType();
        orderType.setOrderNumber( new JAXBElement<>( QNAME, Long.class, 5L ) );
        orderType.setOrderDetails(
            new JAXBElement<>(
                QNAME,
                OrderDetailsType.class,
                new OrderDetailsType()
            )
        );
        orderType.getOrderDetails().getValue().setName( new JAXBElement<>( QNAME, String.class, "test" ) );
        orderType.getOrderDetails().getValue().setDescription( description );
        orderType.setDates( dates );

        return orderType;
    }

    private XMLGregorianCalendar createXmlCal( int year, int month, int day )
            throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance()
                .newXMLGregorianCalendarDate( year, month, day, DatatypeConstants.FIELD_UNDEFINED );
    }

    private SourceType createSource() {
         SourceType source = new SourceType();
         source.setDate( new JAXBElement<>( QNAME, String.class, "06.07.2013" ) );
         return source;
    }

    private TargetDto createTarget() {
         TargetDto target = new TargetDto();
         target.setDate( new GregorianCalendar( 2013, Calendar.JULY, 6 ).getTime() );
         return target;
    }
}
