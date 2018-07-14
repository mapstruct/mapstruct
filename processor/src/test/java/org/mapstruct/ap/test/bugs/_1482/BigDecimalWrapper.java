/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1482;

import java.math.BigDecimal;

public class BigDecimalWrapper implements ValueWrapper<BigDecimal> {

    private final BigDecimal value;

    public BigDecimalWrapper(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }
}
