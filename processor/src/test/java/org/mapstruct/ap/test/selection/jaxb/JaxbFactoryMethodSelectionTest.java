/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb;

import javax.xml.bind.annotation.XmlElementDecl;

import org.mapstruct.ap.test.selection.jaxb.test1.OrderType;
import org.mapstruct.ap.test.selection.jaxb.test2.ObjectFactory;
import org.mapstruct.ap.test.selection.jaxb.test2.OrderShippingDetailsType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxJaxb;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the selection of JAXB mapping and factory methods based on the "name" and "scope" attributes
 * of the {@link XmlElementDecl} annotation.
 *
 * @author Sjaak Derksen
 */
@IssueKey("135")
@WithClasses({
    org.mapstruct.ap.test.selection.jaxb.test1.ObjectFactory.class, ObjectFactory.class,
    OrderDto.class, OrderShippingDetailsDto.class, OrderType.class, OrderShippingDetailsType.class,
    OrderMapper.class
})
@WithJavaxJaxb
public class JaxbFactoryMethodSelectionTest {

    @ProcessorTest
    public void shouldMatchOnNameAndOrScope() {
        OrderType target = OrderMapper.INSTANCE.targetToSource( createSource() );

        // qname and value should match for orderNumbers (distinct 1, 2)
        assertThat( target.getOrderNumber1().getValue() ).isEqualTo( 15L );
        assertThat( target.getOrderNumber1().getName() ).isEqualTo(
            org.mapstruct.ap.test.selection.jaxb.test1.ObjectFactory.ORDER_TYPE_ORDER_NUMBER1_QNAME
        );
        assertThat( target.getOrderNumber2().getValue() ).isEqualTo( 31L );
        assertThat( target.getOrderNumber2().getName() ).isEqualTo(
            org.mapstruct.ap.test.selection.jaxb.test1.ObjectFactory.ORDER_TYPE_ORDER_NUMBER2_QNAME
        );

        // qname should match for shipping details
        assertThat( target.getShippingDetails().getName() ).isEqualTo(
            org.mapstruct.ap.test.selection.jaxb.test1.ObjectFactory.ORDER_TYPE_SHIPPING_DETAILS_QNAME
        );

        OrderShippingDetailsType shippingDetails = target.getShippingDetails().getValue();

        // qname and value should match (ObjectFactory = test2.ObjectFactory)
        assertThat( shippingDetails.getOrderShippedFrom().getValue() ).isEqualTo( "from" );
        assertThat( shippingDetails.getOrderShippedFrom().getName() ).isEqualTo(
            ObjectFactory.ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_FROM_QNAME
        );
        assertThat( shippingDetails.getOrderShippedTo().getValue() ).isEqualTo( "to" );
        assertThat( shippingDetails.getOrderShippedTo().getName() ).isEqualTo(
            ObjectFactory.ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_TO_QNAME
        );
    }

    private OrderDto createSource() {
        OrderDto order = new OrderDto();
        order.setShippingDetails( new OrderShippingDetailsDto() );
        order.setOrderNumber1( 15L );
        order.setOrderNumber2( 31L );
        order.getShippingDetails().setOrderShippedFrom( "from" );
        order.getShippingDetails().setOrderShippedTo( "to" );
        return order;
    }
}
