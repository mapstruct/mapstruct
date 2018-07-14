/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.mappingTarget.simple;

public class SimpleImmutableTarget {
    private final String name;
    private final int age;

    SimpleImmutableTarget(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;
        private int age;

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public SimpleImmutableTarget build() {
            return new SimpleImmutableTarget( this );
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}
