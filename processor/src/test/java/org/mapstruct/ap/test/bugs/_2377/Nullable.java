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
public class Nullable<T> {

    @SuppressWarnings("rawtypes")
    private static final Nullable UNDEFINED = new Nullable<>( null, false );

    private final T value;
    private final boolean present;

    private Nullable(T value, boolean present) {
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

    public static <T> Nullable<T> of(T value) {
        return new Nullable<>( value, true );
    }

    @SuppressWarnings("unchecked")
    public static <T> Nullable<T> undefined() {
        return (Nullable<T>) UNDEFINED;
    }
}
