/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb;

/**
 * @author Sjaak Derksen
 */
public class OrderShippingDetailsDto {

    private String orderShippedFrom;
    private String orderShippedTo;

    public String getOrderShippedFrom() {
        return orderShippedFrom;
    }

    public void setOrderShippedFrom(String orderShippedFrom) {
        this.orderShippedFrom = orderShippedFrom;
    }

    public String getOrderShippedTo() {
        return orderShippedTo;
    }

    public void setOrderShippedTo(String orderShippedTo) {
        this.orderShippedTo = orderShippedTo;
    }
}
