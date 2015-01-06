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


    public ObjectFactory() {
    }

    public OrderType createOrderType() {
        return new OrderType();
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1", name = "Order")
    public JAXBElement<OrderType> createOrder(OrderType value) {
        return new JAXBElement<OrderType>( ORDER_QNAME, OrderType.class, null, value );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        name = "orderNumber1", scope = OrderType.class)
    public JAXBElement<Long> createOrderTypeOrderNumber1(Long value) {
        return new JAXBElement<Long>( ORDER_TYPE_ORDER_NUMBER1_QNAME, Long.class, OrderType.class, value );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        name = "orderNumber2", scope = OrderType.class)
    public JAXBElement<Long> createOrderTypeOrderNumber2(Long value) {
        return new JAXBElement<Long>( ORDER_TYPE_ORDER_NUMBER2_QNAME, Long.class, OrderType.class, value );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        name = "shippingDetails", scope = OrderType.class)
    public JAXBElement<OrderShippingDetailsType> createOrderTypeShippingDetails(OrderShippingDetailsType value) {
        return new JAXBElement<OrderShippingDetailsType>(
            ORDER_TYPE_SHIPPING_DETAILS_QNAME,
            OrderShippingDetailsType.class, OrderType.class, value
        );
    }

}
