/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3667;

public class Source {

    private final Nested nested;

    public Source(Nested nested) {
        this.nested = nested;
    }

    public Nested getNested() {
        return nested;
    }

    public static class Nested {

        private final NestedNested nested1;
        private final NestedNested nested2;

        public Nested(NestedNested nested1, NestedNested nested2) {
            this.nested1 = nested1;
            this.nested2 = nested2;
        }

        public NestedNested getNested1() {
            return nested1;
        }

        public NestedNested getNested2() {
            return nested2;
        }
    }

    public static class NestedNested {

        private final String value;

        public NestedNested(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
