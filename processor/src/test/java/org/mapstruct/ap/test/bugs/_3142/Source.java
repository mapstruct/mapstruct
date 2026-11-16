/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3142;

/**
 * @author Filip Hrisafov
 */
public class Source {
    private final Nested nested;

    public Source(Nested nested) {
        this.nested = nested;
    }

    public Nested getNested() {
        return nested;
    }

    public static class Nested {
        private final String value;

        public Nested(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
