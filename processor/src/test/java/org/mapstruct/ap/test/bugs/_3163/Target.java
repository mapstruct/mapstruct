/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3163;

import java.util.Optional;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private Nested nested;

    public Optional<Nested> getNested() {
        return Optional.ofNullable( nested );
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public final void setNested(Optional<? extends Nested> nested) {
        this.nested = nested.orElse( null );
    }

    public static class Nested {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
