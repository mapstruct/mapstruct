/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3949;

import java.time.LocalDate;

public interface TargetStringInterface {
    void setDate(LocalDate date);

    void setDate(String date);

    String getDate();

    LocalDate getDateValue();
}
