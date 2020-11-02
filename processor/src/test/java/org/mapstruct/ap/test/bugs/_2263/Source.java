/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2263;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private final Object value;

    public Source(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
