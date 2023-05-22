/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.nested;

import org.mapstruct.ap.test.sourceannotation.StringConversion;

public class FirstSource {

    private final String firstProperty;

    public FirstSource(String firstProperty) {
        this.firstProperty = firstProperty;
    }

    @StringConversion("toUpperCase")
    public String getFirstProperty() {
        return firstProperty;
    }

}
