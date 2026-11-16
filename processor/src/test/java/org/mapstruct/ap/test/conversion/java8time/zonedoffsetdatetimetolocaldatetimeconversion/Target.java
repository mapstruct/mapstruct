/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion;

import java.time.Instant;
import java.time.LocalDateTime;

public class Target {
    private LocalDateTime zonedDateTime;
    private LocalDateTime offsetDateTime;
    private Instant zonedDateTimeAsInstant;
    private Instant offsetDateTimeAsInstant;

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

    public Instant getZonedDateTimeAsInstant() {
        return zonedDateTimeAsInstant;
    }

    public void setZonedDateTimeAsInstant(Instant zonedDateTimeAsInstant) {
        this.zonedDateTimeAsInstant = zonedDateTimeAsInstant;
    }

    public Instant getOffsetDateTimeAsInstant() {
        return offsetDateTimeAsInstant;
    }

    public void setOffsetDateTimeAsInstant(Instant offsetDateTimeAsInstant) {
        this.offsetDateTimeAsInstant = offsetDateTimeAsInstant;
    }
}
