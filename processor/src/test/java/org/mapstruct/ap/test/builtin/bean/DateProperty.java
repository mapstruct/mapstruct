/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

import java.util.Date;

public class DateProperty {

    // CHECKSTYLE:OFF
    public Date publicProp;
    // CHECKSTYLE:ON

    private Date prop;

    public Date getProp() {
        return prop;
    }

    public void setProp( Date prop ) {
        this.prop = prop;
    }

}
