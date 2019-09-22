/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class OrderDTO {

    private ItemDTO item;

    private CustomerDTO customer;

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public void setCustomer( CustomerDTO customer ) {
        this.customer = customer;
    }

    public CustomerDTO getCustomer() {
        return this.customer;
    }
}
