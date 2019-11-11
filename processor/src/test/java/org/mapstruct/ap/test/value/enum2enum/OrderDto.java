/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.enum2enum;

import org.mapstruct.ap.test.value.ExternalOrderType;

/**
 * @author Gunnar Morling
 */
public class OrderDto {

    private ExternalOrderType orderType;

    public ExternalOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(ExternalOrderType orderType) {
        this.orderType = orderType;
    }
}
