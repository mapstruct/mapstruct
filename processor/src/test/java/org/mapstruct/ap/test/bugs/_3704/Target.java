/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3704;

import java.util.Optional;

public class Target {

    private Nested date;

    public Optional<Nested> getDate() {
        return Optional.ofNullable( date );
    }

    public void setDate(Optional<? extends Nested> date) {
        this.date = date.orElse( null );
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
