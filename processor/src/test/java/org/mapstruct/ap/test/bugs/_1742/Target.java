/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1742;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private NestedTarget nested;

    public NestedTarget getNested() {
        return nested;
    }

    public void setNested(NestedTarget nested) {
        this.nested = nested;
    }
}
