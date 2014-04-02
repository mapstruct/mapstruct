/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nestedmethodcall;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for the nested invocation of mapping methods.
 *
 * @author Sjaak Derksen
 */
@IssueKey("134")
@WithClasses({
    SourceTargetMapper.class,
    OrderDto.class,
    OrderDetailsDto.class,
    OrderDetailsType.class,
    OrderType.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class NestedMappingMethodInvocationTest {

    private static final QName QNAME = new QName( "dont-care" );

    @Test
    public void shouldGeneratedNestedMappingMethodCalls() throws DatatypeConfigurationException {
        SourceTargetMapper instance = SourceTargetMapper.INSTANCE;
        OrderDto target = instance.sourceToTarget( createOrderType() );

        assertThat( target ).isNotNull();
        assertThat( target.getOrderNumber() ).isEqualTo( 5L );
        assertThat( target.getDates() ).containsExactly( "02.03.1999", "28.07.2004" );

        assertThat( target.getOrderDetails() ).isNotNull();
        assertThat( target.getOrderDetails().getName() ).isEqualTo( "test" );
        assertThat( target.getOrderDetails().getDescription() ).containsExactly( "elem1", "elem2" );
    }

    private OrderType createOrderType() throws DatatypeConfigurationException {
        List<JAXBElement<XMLGregorianCalendar>> dates = new ArrayList<JAXBElement<XMLGregorianCalendar>>();
        dates.add(
            new JAXBElement<XMLGregorianCalendar>(
                QNAME,
                XMLGregorianCalendar.class,
                createXmlCal( 1999, 3, 2 )
            )
        );
        dates.add(
            new JAXBElement<XMLGregorianCalendar>(
                QNAME,
                XMLGregorianCalendar.class,
                createXmlCal( 2004, 7, 28 )
            )
        );

        List<JAXBElement<String>> description = new ArrayList<JAXBElement<String>>();
        description.add( new JAXBElement<String>( QNAME, String.class, "elem1" ) );
        description.add( new JAXBElement<String>( QNAME, String.class, "elem2" ) );

        OrderType orderType = new OrderType();
        orderType.setOrderNumber( new JAXBElement<Long>( QNAME, Long.class, 5L ) );
        orderType.setOrderDetails(
            new JAXBElement<OrderDetailsType>(
                QNAME,
                OrderDetailsType.class,
                new OrderDetailsType()
            )
        );
        orderType.getOrderDetails().getValue().setName( new JAXBElement<String>( QNAME, String.class, "test" ) );
        orderType.getOrderDetails().getValue().setDescription( description );
        orderType.setDates( dates );

        return orderType;
    }

    private XMLGregorianCalendar createXmlCal(int year, int month, int day)
        throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance()
            .newXMLGregorianCalendarDate( year, month, day, DatatypeConstants.FIELD_UNDEFINED );
    }
}
