/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1742;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private NestedSource nested;

    public NestedSource getNested() {
        return nested;
    }

    public void setNested(NestedSource nested) {
        this.nested = nested;
    }
}
