/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class OrderDto {

    private Long orderNumber;
    private OrderDetailsDto orderDetails;
    private List<String> dates;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderDetailsDto getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsDto orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

}
