/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2195.dto;

public class Target extends TargetBase {

    protected Target(Builder builder) {
        super( builder );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends TargetBase.Builder {

        protected Builder() {
        }

        public Target build() {
            return new Target( this );
        }

        public Builder name(String name) {
            return this;
        }
    }
}
