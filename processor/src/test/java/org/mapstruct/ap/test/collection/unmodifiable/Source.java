/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.unmodifiable;

public class Source {
    private final String something;

    public Source(String something) {
        this.something = something;
    }

    public String getSomething() {
        return something;
    }
}
