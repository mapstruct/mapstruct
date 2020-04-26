/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.unmapped;

/**
 * @author Filip Hrisafov
 */
public class OrderDto {

    private final String name;

    public OrderDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
