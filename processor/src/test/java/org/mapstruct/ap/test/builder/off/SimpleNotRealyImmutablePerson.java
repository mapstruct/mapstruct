/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.off;

public class SimpleNotRealyImmutablePerson {

    private String name;

    public static Builder builder() {
        return new Builder();
    }

    public SimpleNotRealyImmutablePerson() {
    }

    SimpleNotRealyImmutablePerson(Builder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private String name;

        public Builder name(String name) {
            throw new IllegalStateException( "name should not be called on builder" );
        }

        public SimpleNotRealyImmutablePerson build() {
            return new SimpleNotRealyImmutablePerson( this );
        }

    }
}
