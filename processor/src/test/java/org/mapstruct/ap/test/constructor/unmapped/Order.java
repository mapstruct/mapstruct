/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.unmapped;

import java.util.Date;

/**
 * @author Filip Hrisafov
 */
public class Order {

    private final String name;
    private final Date time;

    public Order(String name, Date time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Date getTime() {
        return time;
    }
}
