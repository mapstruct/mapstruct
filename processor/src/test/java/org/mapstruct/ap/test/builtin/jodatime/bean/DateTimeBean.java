/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.jodatime.bean;

import org.joda.time.DateTime;

/**
 *
 * @author Sjaak Derksen
 */
public class DateTimeBean {

    private DateTime dateTime;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
