/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1375;

/**
 * @author Filip Hrisafov
 */
public class Target {

    Nested nested;

    public void setNested(Nested nested) {
        this.nested = nested;
    }

    public static class Nested {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
