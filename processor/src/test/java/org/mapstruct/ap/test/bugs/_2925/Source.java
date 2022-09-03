/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2925;

public class Source {

    private final long value;

    public Source(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
