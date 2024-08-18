/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3673;

public class Animal {

    private AnimalDetails details;

    public AnimalDetails getDetails() {
        return details;
    }

    public void setDetails(AnimalDetails details) {
        this.details = details;
    }

    public enum Type {
        CAT,
        DOG
    }

    public static class AnimalDetails {
        private Type type;
        private String name;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
