/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3704;

import java.util.Optional;

public class Source {

    private final Nested date;

    public Source(Nested date) {
        this.date = date;
    }

    public Optional<Nested> getDate() {
        return Optional.ofNullable( date );
    }

    public static class Nested {

        private final String value;

        public Nested(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
