/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3142;

/**
 * @author Filip Hrisafov
 */
public class Target {
    private String id;
    private Nested nested;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Nested getNested() {
        return nested;
    }

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
