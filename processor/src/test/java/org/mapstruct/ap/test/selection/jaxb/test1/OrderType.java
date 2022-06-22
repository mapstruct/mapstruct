/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb.test1;

import java.util.List;

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
    @XmlElementRef(name = "description", namespace = "http://www.mapstruct.org/itest/jaxb/xsd/test1",
        type = JAXBElement.class)
    protected List<JAXBElement<String>> description;

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

    public List<JAXBElement<String>> getDescription() {
        return description;
    }

    public void setDescription(List<JAXBElement<String>> description) {
        this.description = description;
    }

}
