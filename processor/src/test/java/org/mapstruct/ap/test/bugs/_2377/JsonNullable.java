/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2377;

import java.util.NoSuchElementException;

/**
 * @author Filip Hrisafov
 */
public class JsonNullable<T> {

    @SuppressWarnings("rawtypes")
    private static final JsonNullable UNDEFINED = new JsonNullable<>( null, false );

    private final T value;
    private final boolean present;

    private JsonNullable(T value, boolean present) {
        this.value = value;
        this.present = present;
    }

    public T get() {
        if (!present) {
            throw new NoSuchElementException("Value is undefined");
        }
        return value;
    }

    public boolean isPresent() {
        return present;
    }

    @SuppressWarnings("unchecked")
    public static <T> JsonNullable<T> undefined() {
        return (JsonNullable<T>) UNDEFINED;
    }

    public static <T> JsonNullable<T> of(T value) {
        return new JsonNullable<>( value, true );
    }
}
