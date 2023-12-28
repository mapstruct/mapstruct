/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3463;

/**
 * @author Filip Hrisafov
 */
public class Person {

    private final String name;

    private Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public interface Builder extends EntityBuilder<Person> {

        Builder name(String name);
    }

    private static final class BuilderImpl implements Builder {

        private String name;

        private BuilderImpl() {
        }

        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Person build() {
            return new Person( this.name );
        }
    }
}
