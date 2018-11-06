/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1576.java8;

import java.util.Date;

public class Source {

    private Date localDateTime;
    private Date localDate;
    private String localTime;
    private Date zonedDateTime;
    private Date instant;

    public Date getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(Date localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Date getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date localDate) {
        this.localDate = localDate;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public Date getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(Date zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instant) {
        this.instant = instant;
    }
}
