/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1738;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private Nested<? extends Number> nested;

    public Nested<? extends Number> getNested() {
        return nested;
    }

    public void setNested(Nested<? extends Number> nested) {
        this.nested = nested;
    }

    public static class Nested<T> {

        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
