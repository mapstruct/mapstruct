/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1681;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private String value;

    public Target(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String builderValue;

        public String getBuilderValue() {
            return builderValue;
        }

        public Builder builderValue(String builderValue) {
            this.builderValue = builderValue;
            return this;
        }

        public Target build() {
            return new Target( builderValue );
        }
    }
}
