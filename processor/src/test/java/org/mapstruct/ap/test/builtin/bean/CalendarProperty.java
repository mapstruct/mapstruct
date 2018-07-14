/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

import java.util.Calendar;

public class CalendarProperty {

    // CHECKSTYLE:OFF
    public Calendar publicProp;
    // CHECKSTYLE:ON

    private Calendar prop;

    public Calendar getProp() {
        return prop;
    }

    public void setProp( Calendar prop ) {
        this.prop = prop;
    }
}
