/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exclusions.custom;

/**
 * @author Filip Hrisafov
 */
// tag::documentation[]
public class Target {

    static class NestedTarget {
        private String property;
        // getters and setters
        // end::documentation[]

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
        // tag::documentation[]
    }

    private NestedTarget nested;
    // getters and setters
    // end::documentation[]

    public NestedTarget getNested() {
        return nested;
    }

    public void setNested(NestedTarget nested) {
        this.nested = nested;
    }
    // tag::documentation[]
}
// end::documentation[]
