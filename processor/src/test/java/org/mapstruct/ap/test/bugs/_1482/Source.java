/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1482;

public class Source {

    private SourceEnum test;
    private BigDecimalWrapper wrapper;

    public SourceEnum getTest() {
        return test;
    }

    public void setTest(SourceEnum test) {
        this.test = test;
    }

    public BigDecimalWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(BigDecimalWrapper wrapper) {
        this.wrapper = wrapper;
    }
}
