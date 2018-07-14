/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb;

import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class OrderDto {

    private Long orderNumber1;
    private Long orderNumber2;
    private OrderShippingDetailsDto shippingDetails;
    private List<String> description;

    public Long getOrderNumber1() {
        return orderNumber1;
    }

    public void setOrderNumber1(Long orderNumber1) {
        this.orderNumber1 = orderNumber1;
    }

    public Long getOrderNumber2() {
        return orderNumber2;
    }

    public void setOrderNumber2(Long orderNumber2) {
        this.orderNumber2 = orderNumber2;
    }

    public OrderShippingDetailsDto getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(OrderShippingDetailsDto shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

}
