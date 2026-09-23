/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2932;

/**
 * Mirrors the shape Lombok generates for {@code @Builder} on a generic class.
 */
public class Record<T> {

    private final String name;
    private final T value;

    private Record(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public static <T> RecordBuilder<T> builder() {
        return new RecordBuilder<>();
    }

    public static class RecordBuilder<T> {

        private String name;
        private T value;

        RecordBuilder() {
        }

        public RecordBuilder<T> name(String name) {
            this.name = name;
            return this;
        }

        public RecordBuilder<T> value(T value) {
            this.value = value;
            return this;
        }

        public Record<T> build() {
            return new Record<>( name, value );
        }
    }
}
