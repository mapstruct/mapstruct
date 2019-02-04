/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import java.util.ArrayList;
import java.util.List;

public class OrderItem extends Item {

    public void addOrderLine( OrderLine orderLine ) {
        if ( null == this.orderLines ) {
            this.orderLines = new ArrayList<OrderLine>();
        }

        this.orderLines.add( orderLine );
    }

    public void setOrderLines( List<OrderLine> orderLines ) {
        this.orderLines = orderLines;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    private List<OrderLine> orderLines;

    private CustomerItem customer;

    public void setCustomer( CustomerItem customer ) {
        this.customer = customer;
    }

    public CustomerItem getCustomer() {
        return this.customer;
    }
}
