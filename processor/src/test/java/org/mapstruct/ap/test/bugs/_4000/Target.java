/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4000;

public class Target {

    private final String xNameField;

    public Target(Builder builder) {
        this.xNameField = builder.xNameField;
    }

    public String getXNameField() {
        return xNameField;
    }

    public static Target.Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String xNameField;

        public Builder xNameField(String xNameField) {
            this.xNameField = xNameField;
            return this;
        }

        public Target build() {
            return new Target( this );
        }
    }
}
