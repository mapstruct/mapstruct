/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion;

import java.time.LocalDateTime;

public class Target {
    private LocalDateTime zonedDateTime;
    private LocalDateTime offsetDateTime;

    public LocalDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(LocalDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public LocalDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(LocalDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }
}
