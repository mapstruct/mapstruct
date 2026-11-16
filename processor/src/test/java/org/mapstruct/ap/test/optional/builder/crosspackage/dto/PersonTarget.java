/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.builder.crosspackage.dto;

public class PersonTarget {

    private final String name;

    private PersonTarget(PersonTargetBuilder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static PersonTargetBuilder builder() {
        return new PersonTargetBuilder();
    }

    public static class PersonTargetBuilder {

        private String name;

        PersonTargetBuilder() { }

        public PersonTargetBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonTarget build() {
            return new PersonTarget( this );
        }
    }
}
