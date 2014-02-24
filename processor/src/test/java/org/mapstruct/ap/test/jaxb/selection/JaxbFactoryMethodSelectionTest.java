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
package org.mapstruct.ap.test.jaxb.selection;

import org.mapstruct.ap.test.jaxb.selection.test1.OrderType;
import org.mapstruct.ap.test.jaxb.selection.test2.OrderShippingDetailsType;
import org.mapstruct.ap.test.jaxb.selection.test2.ObjectFactory;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;

import org.testng.annotations.Test;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@IssueKey("135")
@WithClasses({ org.mapstruct.ap.test.jaxb.selection.test1.ObjectFactory.class, ObjectFactory.class,
    OrderDto.class, OrderShippingDetailsDto.class, OrderType.class, OrderShippingDetailsType.class,
    SourceTargetMapper.class
})
public class JaxbFactoryMethodSelectionTest extends MapperTestBase {


    @Test
    public void shouldMatchOnNameAndOrScope() {
         OrderType target = SourceTargetMapper.INSTANCE.targetToSource( createSource() );

         // qname and value should match for orderNumbers (distinct 1, 2)
         assertThat( target.getOrderNumber1().getValue() ).isEqualTo( 15L );
         assertThat( target.getOrderNumber1().getName() ).isEqualTo(
                 org.mapstruct.ap.test.jaxb.selection.test1.ObjectFactory.ORDER_TYPE_ORDER_NUMBER1_QNAME );
         assertThat( target.getOrderNumber2().getValue() ).isEqualTo( 31L );
         assertThat( target.getOrderNumber2().getName() ).isEqualTo(
                 org.mapstruct.ap.test.jaxb.selection.test1.ObjectFactory.ORDER_TYPE_ORDER_NUMBER2_QNAME );

         // qname should match for shipping details
         assertThat( target.getShippingDetails().getName() ).isEqualTo(
                 org.mapstruct.ap.test.jaxb.selection.test1.ObjectFactory.ORDER_TYPE_SHIPPING_DETAILS_QNAME );

         OrderShippingDetailsType shippingDetals = target.getShippingDetails().getValue();

         // qname and value should match (ObjectFactory = test2.ObjectFactory)
         assertThat( shippingDetals.getOrderShippedFrom().getValue() ).isEqualTo( "from" );
         assertThat( shippingDetals.getOrderShippedFrom().getName() ).isEqualTo(
                 ObjectFactory.ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_FROM_QNAME );
         assertThat( shippingDetals.getOrderShippedTo().getValue() ).isEqualTo( "to" );
         assertThat( shippingDetals.getOrderShippedTo().getName() ).isEqualTo(
                 ObjectFactory.ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_TO_QNAME );
    }

    private OrderDto createSource() {
        OrderDto order = new OrderDto();
        order.setShippingDetails(  new OrderShippingDetailsDto() );
        order.setOrderNumber1( 15L );
        order.setOrderNumber2( 31L );
        order.getShippingDetails().setOrderShippedFrom( "from" );
        order.getShippingDetails().setOrderShippedTo( "to" );
        return order;
    }
}
