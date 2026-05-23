/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class Source {
    private ZonedDateTime zonedDateTime;
    private OffsetDateTime offsetDateTime;
    private ZonedDateTime zonedDateTimeAsInstant;
    private OffsetDateTime offsetDateTimeAsInstant;

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public ZonedDateTime getZonedDateTimeAsInstant() {
        return zonedDateTimeAsInstant;
    }

    public void setZonedDateTimeAsInstant(ZonedDateTime zonedDateTimeAsInstant) {
        this.zonedDateTimeAsInstant = zonedDateTimeAsInstant;
    }

    public OffsetDateTime getOffsetDateTimeAsInstant() {
        return offsetDateTimeAsInstant;
    }

    public void setOffsetDateTimeAsInstant(OffsetDateTime offsetDateTimeAsInstant) {
        this.offsetDateTimeAsInstant = offsetDateTimeAsInstant;
    }
}
