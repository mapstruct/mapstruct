/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Sjaak Derksen
 */
public class OrderType {

    private JAXBElement<Long> orderNumber;
    private JAXBElement<OrderDetailsType> orderDetails;
    private List<JAXBElement<XMLGregorianCalendar>> dates;

    public JAXBElement<Long> getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(JAXBElement<Long> value) {
        this.orderNumber = value;
    }

    public JAXBElement<OrderDetailsType> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(JAXBElement<OrderDetailsType> value) {
        this.orderDetails = value;
    }

    public List<JAXBElement<XMLGregorianCalendar>> getDates() {
        return dates;
    }

    public void setDates(List<JAXBElement<XMLGregorianCalendar>> dates) {
        this.dates = dates;
    }

}
