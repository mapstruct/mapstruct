/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3703.entity;

public class Contact {

    private final String name;

    private Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Contact build() {
            return new Contact( name );
        }
    }
}
