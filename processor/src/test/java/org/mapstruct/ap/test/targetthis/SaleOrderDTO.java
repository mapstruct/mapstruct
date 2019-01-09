/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class SaleOrderDTO {
    private ItemDTO item;

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    private OrderDTO order;

    public void setOrder( OrderDTO order ) {
        this.order = order;
    }

    public OrderDTO getOrder() {
        return this.order;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber( String number ) {
        this.number = number;
    }

    private String number;
}
