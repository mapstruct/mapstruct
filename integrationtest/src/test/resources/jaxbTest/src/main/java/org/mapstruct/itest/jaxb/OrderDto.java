/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jaxb;

import java.util.Date;

/**
 * @author Sjaak Derksen
 */
public class OrderDto {

    private Long orderNumber;
    private Date orderDate;
    private OrderDetailsDto orderDetails;
    private ShippingAddressDto shippingAddress;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderDetailsDto getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsDto orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ShippingAddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
