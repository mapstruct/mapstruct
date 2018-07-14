/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractGenericTarget;

public class ImmutableChild implements Child {
    private final String name;

    private ImmutableChild(ImmutableChild.Builder builder) {
        this.name = builder.name;
    }

    public static ImmutableChild.Builder builder() {
        return new ImmutableChild.Builder();
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;

        public ImmutableChild.Builder name(String name) {
            this.name = name;
            return this;
        }

        public ImmutableChild build() {
            return new ImmutableChild( this );
        }
    }
}
