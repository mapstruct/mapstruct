/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import java.util.List;
import java.util.Map;

/**
 * @author Sjaak Derksen
 */
public class CustomerDto {

    private Long id;
    private String customerName;
    private List<OrderItemDto> orders;
    private Map<OrderItemKeyDto, OrderItemDto> stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItemDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItemDto> orders) {
        this.orders = orders;
    }

    public Map<OrderItemKeyDto, OrderItemDto> getStock() {
        return stock;
    }

    public void setStock(Map<OrderItemKeyDto, OrderItemDto> stock) {
        this.stock = stock;
    }
}
