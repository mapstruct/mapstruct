/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import java.util.ArrayList;
import java.util.List;

public class OrderEntity extends Entity {

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

    private CustomerEntity customer;

    public void setCustomer( CustomerEntity customer ) {
        this.customer = customer;
    }

    public CustomerEntity getCustomer() {
        return this.customer;
    }
}
