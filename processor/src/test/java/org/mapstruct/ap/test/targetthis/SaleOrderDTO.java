/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

public class SaleOrderDTO {
    private EntityDTO entity;

    public EntityDTO getEntity() {
        return entity;
    }

    public void setEntity(EntityDTO entity) {
        this.entity = entity;
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
