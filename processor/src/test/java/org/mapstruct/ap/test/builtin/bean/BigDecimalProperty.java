/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.bean;

import java.math.BigDecimal;

public class BigDecimalProperty {

    // CHECKSTYLE:OFF
    public BigDecimal publicProp;
    // CHECKSTYLE:ON

    private BigDecimal prop;

    public BigDecimal getProp() {
        return prop;
    }

    public void setProp( BigDecimal prop ) {
        this.prop = prop;
    }
}
