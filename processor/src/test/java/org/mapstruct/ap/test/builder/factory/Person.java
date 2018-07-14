/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.factory;

/**
 * @author Filip Hrisafov
 */
public class Person {

    private final String name;
    private final String source;

    protected Person(PersonBuilder builder) {
        this.name = builder.name;
        this.source = builder.source;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public static PersonBuilder builder() {
        throw new UnsupportedOperationException( "Factory should be used" );
    }

    public static class PersonBuilder {
        private String name;
        private final String source;

        public PersonBuilder(String source) {
            this.source = source;
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Person create() {
            return new Person( this );
        }
    }
}
