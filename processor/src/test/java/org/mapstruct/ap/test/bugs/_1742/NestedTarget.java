/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1742;

/**
 * @author Filip Hrisafov
 */
public class NestedTarget {

    private String value;

    public NestedTarget() {

    }

    public NestedTarget(Builder builder) {
        this.value = getValue();
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class Builder {

        private String value;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public NestedTarget create() {
            return new NestedTarget(this);
        }
    }
}
