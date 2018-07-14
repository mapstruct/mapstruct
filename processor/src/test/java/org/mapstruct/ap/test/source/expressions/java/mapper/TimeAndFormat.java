/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java.mapper;

import java.util.Date;

/**
 * @author Sjaak Derksen
 */
public class TimeAndFormat {
    private Date time;
    private String format;

    public TimeAndFormat(Date time, String format) {
        this.time = time;
        this.format = format;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String tfFormat) {
        this.format = tfFormat;
    }
}
