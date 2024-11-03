/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3707;

public abstract class AbstractDto {
    private final String name;
    private final Integer id;

    public abstract static class Builder<T> {
        private String name;
        private Integer id;

        protected abstract T self();

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T id(Integer id) {
            this.id = id;
            return self();
        }

        public abstract AbstractDto build();
    }

    protected AbstractDto(Builder<?> builder) {
        this.name = builder.name;
        this.id = builder.id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
