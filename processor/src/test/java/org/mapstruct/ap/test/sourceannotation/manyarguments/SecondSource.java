/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.manyarguments;

import org.mapstruct.ap.test.sourceannotation.StringConversion;

public class SecondSource {

    // CHECKSTYLE:OFF
    @StringConversion("toLowerCase")
    public final String secondProperty;
    // CHECKSTYLE:ON

    public SecondSource(String secondProperty) {
        this.secondProperty = secondProperty;
    }

}
