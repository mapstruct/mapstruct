/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.java8time.bean;

import java.time.ZonedDateTime;

/**
 * @author Andreas Gudian
 */
public class ZonedDateTimeProperty {

    // CHECKSTYLE:OFF
    public ZonedDateTime publicProp;
    // CHECKSTYLE:ON

    private ZonedDateTime prop;

    public ZonedDateTime getProp() {
        return prop;
    }

    public void setProp(ZonedDateTime prop) {
        this.prop = prop;
    }
}
