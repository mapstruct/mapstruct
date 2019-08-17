/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb.test1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import org.mapstruct.ap.test.selection.jaxb.test2.OrderShippingDetailsType;

@XmlRegistry
public class ObjectFactory {

    public static final QName ORDER_QNAME =
        new QName( "http://www.mapstruct.org/ap/test/jaxb/selection/test1", "Order" );
    public static final QName ORDER_TYPE_ORDER_NUMBER1_QNAME =
        new QName( "http://www.mapstruct.org/ap/test/jaxb/selection/test1", "orderNumber1" );
    public static final QName ORDER_TYPE_ORDER_NUMBER2_QNAME =
        new QName( "http://www.mapstruct.org/ap/test/jaxb/selection/test1", "orderNumber2" );
    public static final QName ORDER_TYPE_SHIPPING_DETAILS_QNAME =
        new QName( "http://www.mapstruct.org/ap/test/jaxb/selection/test1", "shippingDetails" );
    public static final QName ORDER_TYPE_DESCRIPTION_QNAME =
        new QName("http://www.mapstruct.org/itest/jaxb/xsd/test1", "description");

    public ObjectFactory() {
    }

    public OrderType createOrderType() {
        return new OrderType();
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1", name = "Order")
    public JAXBElement<OrderType> createOrder(OrderType value) {
        return new JAXBElement<>( ORDER_QNAME, OrderType.class, null, value );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        name = "orderNumber1", scope = OrderType.class)
    public JAXBElement<Long> createOrderTypeOrderNumber1(Long value) {
        return new JAXBElement<>( ORDER_TYPE_ORDER_NUMBER1_QNAME, Long.class, OrderType.class, value );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        name = "orderNumber2", scope = OrderType.class)
    public JAXBElement<Long> createOrderTypeOrderNumber2(Long value) {
        return new JAXBElement<>( ORDER_TYPE_ORDER_NUMBER2_QNAME, Long.class, OrderType.class, value );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        name = "shippingDetails", scope = OrderType.class)
    public JAXBElement<OrderShippingDetailsType> createOrderTypeShippingDetails(OrderShippingDetailsType value) {
        return new JAXBElement<>(
            ORDER_TYPE_SHIPPING_DETAILS_QNAME,
            OrderShippingDetailsType.class, OrderType.class, value
        );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/itest/jaxb/xsd/test1",
        name = "description", scope = OrderType.class)
    public JAXBElement<String> createOrderTypeDescription(String value) {
        return new JAXBElement<>( ORDER_TYPE_DESCRIPTION_QNAME, String.class, OrderType.class, value );
    }

}
