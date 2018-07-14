/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.noop;

/**
 * @author Filip Hrisafov
 */
public class Person {

    private String name;

    public Person() {

    }

    public Person(Builder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Builder create() {
        throw new UnsupportedOperationException( "Creating builder is not supported" );
    }

    public static class Builder {

        private String name;

        public Builder() {
            throw new UnsupportedOperationException( "Creating a builder is not supported" );
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Person create() {
            return new Person(this);
        }

    }
}
