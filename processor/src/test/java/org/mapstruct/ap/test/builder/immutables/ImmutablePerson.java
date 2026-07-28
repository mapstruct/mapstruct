/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables;

/**
 * Hand-crafted stand-in for the class that the Immutables annotation processor generates for {@link Person}.
 * Immutables generates a class named {@code Immutable{TypeName}} that contains a nested {@code Builder} with a
 * static {@code builder()} factory on the outer class — the pattern that {@link
 * org.mapstruct.ap.spi.ImmutablesBuilderProvider} redirects MapStruct to when processing a
 * {@code @Value.Immutable}-annotated interface.
 */
public class ImmutablePerson implements Person {

    private final String name;
    private final int age;

    private ImmutablePerson(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private int age;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public ImmutablePerson build() {
            return new ImmutablePerson( this );
        }
    }
}
