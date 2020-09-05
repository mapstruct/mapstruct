/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2195.dto;

public class TargetBase {

    private final String name;

    protected TargetBase(Builder builder) {
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        protected Builder() {
        }

        private String name;

        public TargetBase build() {
            return new TargetBase( this );
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}
