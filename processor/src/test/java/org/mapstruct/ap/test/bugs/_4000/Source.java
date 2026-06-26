/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4000;

public class Source {

    private final String xNameField;

    public Source(String xNameField) {
        this.xNameField = xNameField;
    }

    public String getXNameField() {
        return xNameField;
    }
}
