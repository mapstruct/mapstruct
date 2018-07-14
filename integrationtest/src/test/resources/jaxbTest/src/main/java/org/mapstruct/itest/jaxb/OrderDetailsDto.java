/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jaxb;

import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class OrderDetailsDto {

    private String name;
    private List<String> description;
    private OrderStatusDto status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public OrderStatusDto getStatus() {
        return status;
    }

    public void setStatus(OrderStatusDto status) {
        this.status = status;
    }
}
