/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation;

public class Target {
    private final String firstProperty;
    private final String secondProperty;

    public Target(String firstProperty, String secondProperty) {
        this.firstProperty = firstProperty;
        this.secondProperty = secondProperty;
    }

    public String getFirstProperty() {
        return firstProperty;
    }

    public String getSecondProperty() {
        return secondProperty;
    }
}
