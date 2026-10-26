/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targettype;

import java.time.LocalDate;

public class Target {

    private LocalDate value;

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
}
