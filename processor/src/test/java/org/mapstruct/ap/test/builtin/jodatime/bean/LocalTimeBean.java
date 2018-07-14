/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builtin.jodatime.bean;

import org.joda.time.LocalTime;

/**
 *
 * @author Sjaak Derksen
 */
public class LocalTimeBean {

    private LocalTime localTime;

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }
}
