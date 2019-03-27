/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1772;

/**
 * @author Sjaak Derksen
 */
public class Source {

    private NestedSource nestedSource;

    public NestedSource getNestedSource() {
        return nestedSource;
    }

    public void setNestedSource(NestedSource nestedSource) {
        this.nestedSource = nestedSource;
    }

    public static class NestedSource {

        private double doublyNestedSourceField;

        public double getDoublyNestedSourceField() {
            return doublyNestedSourceField;
        }

        public void setDoublyNestedSourceField(double doublyNestedSourceField) {
            this.doublyNestedSourceField = doublyNestedSourceField;
        }
    }
}
