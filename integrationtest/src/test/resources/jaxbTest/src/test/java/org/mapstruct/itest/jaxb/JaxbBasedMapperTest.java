/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.itest.jaxb;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.mapstruct.itest.jaxb.xsd.test1.ObjectFactory;
import org.mapstruct.itest.jaxb.xsd.test1.OrderType;

/**
 * Test for generation of JAXB based mapper implementations.
 *
 * @author Sjaak Derksen
 */
public class JaxbBasedMapperTest {
    @Test
    public void shouldMapJaxb() throws ParseException, JAXBException {

        SourceTargetMapper mapper = SourceTargetMapper.INSTANCE;

        OrderDto source1 = new OrderDto();
        source1.setOrderDetails( new OrderDetailsDto() );
        source1.setOrderNumber( 11L );
        source1.setOrderDate( createDate( "31-08-1982 10:20:56" ) );
        source1.setShippingAddress( new ShippingAddressDto() );
        source1.getShippingAddress().setCity( "SmallTown" );
        source1.getShippingAddress().setHouseNumber( "11a" );
        source1.getShippingAddress().setStreet( "Awesome rd" );
        source1.getShippingAddress().setCountry( "USA" );
        source1.getOrderDetails().setDescription( new ArrayList<String>() );
        source1.getOrderDetails().setName( "Shopping list for a Mapper" );
        source1.getOrderDetails().getDescription().add( "1 MapStruct" );
        source1.getOrderDetails().getDescription().add( "3 Lines of Code" );
        source1.getOrderDetails().getDescription().add( "1 Dose of Luck" );
        source1.getOrderDetails().setStatus( OrderStatusDto.ORDERED );

        // map to JAXB
        OrderType target = mapper.targetToSource( source1 );

        // do a pretty print
        ObjectFactory of = new ObjectFactory();
        System.out.println( toXml( of.createOrder( target ) ) );

        // map back from JAXB
        OrderDto source2 = mapper.sourceToTarget( target );

        // verify that source1 and source 2 are equal
        assertThat( source2.getOrderNumber() ).isEqualTo( source1.getOrderNumber() );
        assertThat( source2.getOrderDate() ).isEqualTo( source1.getOrderDate() );
        assertThat( source2.getOrderDetails().getDescription().size() ).isEqualTo(
            source1.getOrderDetails().getDescription().size()
        );
        assertThat( source2.getOrderDetails().getDescription().get( 0 ) ).isEqualTo(
            source1.getOrderDetails().getDescription().get( 0 )
        );
        assertThat( source2.getOrderDetails().getDescription().get( 1 ) ).isEqualTo(
            source1.getOrderDetails().getDescription().get( 1 )
        );
        assertThat( source2.getOrderDetails().getDescription().get( 2 ) ).isEqualTo(
            source1.getOrderDetails().getDescription().get( 2 )
        );
        assertThat( source2.getOrderDetails().getName() ).isEqualTo( source1.getOrderDetails().getName() );
        assertThat( source2.getOrderDetails().getStatus() ).isEqualTo( source1.getOrderDetails().getStatus() );
    }

    private Date createDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-M-yyyy hh:mm:ss" );
        return sdf.parse( date );
    }

    private String toXml(JAXBElement<?> element) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance( element.getValue().getClass() );
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal( element, baos );
        return baos.toString();
    }
}
