/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private ItemDTO item;

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    private List<OrderLineDTO> orderLines;

    public void addOrderLine( OrderLineDTO orderLine ) {
        if ( null == this.orderLines ) {
            this.orderLines = new ArrayList<OrderLineDTO>();
        }

        this.orderLines.add( orderLine );
    }

    public List<OrderLineDTO> getOrderLines() {
        return orderLines;
    }

    private CustomerDTO customer;

    public void setCustomer( CustomerDTO customer ) {
        this.customer = customer;
    }

    public CustomerDTO getCustomer() {
        return this.customer;
    }
}
