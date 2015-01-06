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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

import org.mapstruct.ap.test.selection.jaxb.test2.OrderShippingDetailsType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderType", propOrder = {
    "orderNumber1",
    "orderNumber2",
    "shippingDetails"
})
public class OrderType {

    @XmlElementRef(name = "orderNumber1", namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        type = JAXBElement.class)
    private JAXBElement<Long> orderNumber1;
    @XmlElementRef(name = "orderNumber2", namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        type = JAXBElement.class)
    private JAXBElement<Long> orderNumber2;
    @XmlElementRef(name = "shippingDetails", namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test1",
        type = JAXBElement.class)
    private JAXBElement<OrderShippingDetailsType> shippingDetails;

    public JAXBElement<Long> getOrderNumber1() {
        return orderNumber1;
    }

    public void setOrderNumber1(JAXBElement<Long> value) {
        this.orderNumber1 = value;
    }

    public JAXBElement<Long> getOrderNumber2() {
        return orderNumber2;
    }

    public void setOrderNumber2(JAXBElement<Long> value) {
        this.orderNumber2 = value;
    }

    public JAXBElement<OrderShippingDetailsType> getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(JAXBElement<OrderShippingDetailsType> value) {
        this.shippingDetails = value;
    }

}
