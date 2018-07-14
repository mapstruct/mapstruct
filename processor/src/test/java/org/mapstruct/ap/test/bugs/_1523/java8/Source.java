/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1523.java8;

import java.time.ZonedDateTime;
import java.util.Calendar;

public class Source {
    private ZonedDateTime value;
    private Calendar value2;

    public ZonedDateTime getValue() {
        return value;
    }

    public void setValue(ZonedDateTime value) {
        this.value = value;
    }

    public Calendar getValue2() {
        return value2;
    }

    public void setValue2(Calendar value2) {
        this.value2 = value2;
    }
}
