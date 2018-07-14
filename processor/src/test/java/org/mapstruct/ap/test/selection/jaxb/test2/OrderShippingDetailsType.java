/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb.test2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderShippingDetailsType", propOrder = {
    "orderShippedFrom",
    "orderShippedTo"
})
public class OrderShippingDetailsType {

    @XmlElementRef(name = "orderShippedFrom",
        namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test2", type = JAXBElement.class)
    private JAXBElement<String> orderShippedFrom;
    @XmlElementRef(name = "orderShippedTo",
        namespace = "http://www.mapstruct.org/ap/test/jaxb/selection/test2", type = JAXBElement.class)
    private JAXBElement<String> orderShippedTo;

    public JAXBElement<String> getOrderShippedFrom() {
        return orderShippedFrom;
    }

    public void setOrderShippedFrom(JAXBElement<String> value) {
        this.orderShippedFrom = value;
    }

    public JAXBElement<String> getOrderShippedTo() {
        return orderShippedTo;
    }

    public void setOrderShippedTo(JAXBElement<String> value) {
        this.orderShippedTo = value;
    }

}
