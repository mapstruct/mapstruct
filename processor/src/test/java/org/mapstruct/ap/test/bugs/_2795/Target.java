/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795;

public class Target {

    private Nested nested;

    public Nested getNested() {
        return nested;
    }

    public void setNested(Nested nested) {
        this.nested = nested;
    }

}
