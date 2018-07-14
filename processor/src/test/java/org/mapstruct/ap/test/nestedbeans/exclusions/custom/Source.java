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
public class Source {

    static class NestedSource {
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

    private NestedSource nested;
    // getters and setters
    // end::documentation[]

    public NestedSource getNested() {
        return nested;
    }

    public void setNested(NestedSource nested) {
        this.nested = nested;
    }
    // tag::documentation[]
}
// tag::documentation[]
