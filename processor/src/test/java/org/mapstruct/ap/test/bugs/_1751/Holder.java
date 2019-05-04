/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1751;

/**
 * @author Filip Hrisafov
 */
public class Holder<T> {

    private final T value;

    public Holder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    // If empty is considered as a builder creation method, this method would be the build method and would
    // lead to a stackoverflow
    @SuppressWarnings("unused")
    public Holder<T> duplicate() {
        return new Holder<>( value );
    }

    // This method should not be considered as builder creation method
    @SuppressWarnings("unused")
    public static <V> Holder<V> empty() {
        return new Holder<>( null );
    }
}
