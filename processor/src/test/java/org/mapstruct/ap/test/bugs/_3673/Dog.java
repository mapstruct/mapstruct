/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3673;

public class Dog {

    private final Details details;

    public Dog(Details details) {
        this.details = details;
    }

    public Details getDetails() {
        return details;
    }
}
