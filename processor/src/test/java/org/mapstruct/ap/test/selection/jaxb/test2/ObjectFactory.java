/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb.test2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    public static final QName ORDER_SHIPPING_DETAILS_QNAME =
        new QName( "http://www.mapstruct.org/ap/test/jaxb/selection/test2", "OrderShippingDetails" );
    public static final QName ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_FROM_QNAME =
        new QName( "http://www.mapstruct.org/ap/test/jaxb/selection/test2", "orderShippedFrom" );
    public static final QName ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_TO_QNAME =
        new QName( "http://www.mapstruct.org/ap/test/jaxb/selection/test2", "orderShippedTo" );

    public ObjectFactory() {
    }

    public OrderShippingDetailsType createOrderShippingDetailsType() {
        return new OrderShippingDetailsType();
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test2", name = "OrderShippingDetails")
    public JAXBElement<OrderShippingDetailsType> createOrderShippingDetails(OrderShippingDetailsType value) {
        return new JAXBElement<>(
            ORDER_SHIPPING_DETAILS_QNAME,
            OrderShippingDetailsType.class, null, value
        );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test2", name = "orderShippedFrom",
        scope = OrderShippingDetailsType.class)
    public JAXBElement<String> createOrderShippingDetailsTypeOrderShippedFrom(String value) {
        return new JAXBElement<>(
            ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_FROM_QNAME, String.class,
            OrderShippingDetailsType.class, value
        );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test2", name = "orderShippedTo",
        scope = OrderShippingDetailsType.class)
    public JAXBElement<String> createOrderShippingDetailsTypeOrderShippedTo(String value) {
        return new JAXBElement<>(
            ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_TO_QNAME, String.class,
            OrderShippingDetailsType.class, value
        );
    }

}
