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
        return new JAXBElement<OrderShippingDetailsType>(
            ORDER_SHIPPING_DETAILS_QNAME,
            OrderShippingDetailsType.class, null, value
        );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test2", name = "orderShippedFrom",
        scope = OrderShippingDetailsType.class)
    public JAXBElement<String> createOrderShippingDetailsTypeOrderShippedFrom(String value) {
        return new JAXBElement<String>(
            ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_FROM_QNAME, String.class,
            OrderShippingDetailsType.class, value
        );
    }

    @XmlElementDecl(namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test2", name = "orderShippedTo",
        scope = OrderShippingDetailsType.class)
    public JAXBElement<String> createOrderShippingDetailsTypeOrderShippedTo(String value) {
        return new JAXBElement<String>(
            ORDER_SHIPPING_DETAILS_TYPE_ORDER_SHIPPED_TO_QNAME, String.class,
            OrderShippingDetailsType.class, value
        );
    }

}
