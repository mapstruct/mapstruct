/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.ignore;

/**
 * @author Filip Hrisafov
 */
public class Person extends BaseEntity {

    private final String name;
    private final String lastName;

    public Person(Builder builder) {
        super( builder );
        this.name = builder.name;
        this.lastName = builder.lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public static Builder builder() {
        return new Builder();

    }

    public static class Builder extends BaseEntity.Builder {
        private String name;
        private String lastName;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Person create() {
            return new Person( this );
        }
    }
}
