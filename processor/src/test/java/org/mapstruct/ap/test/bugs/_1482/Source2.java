/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1482;

import java.math.BigDecimal;

public class Source2 {

    private Enum<SourceEnum> test;
    private ValueWrapper<BigDecimal> wrapper;

    public Enum<SourceEnum> getTest() {
        return test;
    }

    public void setTest(Enum<SourceEnum> test) {
        this.test = test;
    }

    public ValueWrapper<BigDecimal> getWrapper() {
        return wrapper;
    }

    public void setWrapper(ValueWrapper<BigDecimal> wrapper) {
        this.wrapper = wrapper;
    }
}
