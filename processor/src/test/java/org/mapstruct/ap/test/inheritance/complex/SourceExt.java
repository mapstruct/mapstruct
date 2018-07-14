/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

public class SourceExt extends SourceBase implements AdditionalFooSource {

    private Long bar;

    private int additionalFoo;

    public Long getBar() {
        return bar;
    }

    public void setBar(Long bar) {
        this.bar = bar;
    }

    @Override
    public int getAdditionalFoo() {
        return additionalFoo;
    }

    public void setAdditionalFoo(int additionalFoo) {
        this.additionalFoo = additionalFoo;
    }
}
