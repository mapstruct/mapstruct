/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4081;

import org.jspecify.annotations.NonNull;

public class Target {

    private final Payload payload;

    public Target(@NonNull Payload payload) {
        this.payload = payload;
    }

    public @NonNull Payload getPayload() {
        return payload;
    }

    public static class Payload {

        private final String value;

        public Payload(@NonNull String value) {
            this.value = value;
        }

        public @NonNull String getValue() {
            return value;
        }
    }
}
