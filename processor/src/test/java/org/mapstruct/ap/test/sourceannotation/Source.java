/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation;

public class Source {

    private final String firstProperty;

    // CHECKSTYLE:OFF
    @StringConversion("toLowerCase")
    public final String secondProperty;
    // CHECKSTYLE:ON

    public Source(String firstProperty, String secondProperty) {
        this.firstProperty = firstProperty;
        this.secondProperty = secondProperty;
    }

    @StringConversion("toUpperCase")
    public String getFirstProperty() {
        return firstProperty;
    }

}
