/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.manysourcearguments;

import java.time.LocalDate;

public class ExampleSource {

    private final LocalDate birthday;

    public ExampleSource(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
