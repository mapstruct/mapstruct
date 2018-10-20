/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manytargetproperties;

/**
 * @author Sjaak Derksen
 */
public class Source {

    private TimeAndFormat timeAndFormat;

    private String name;

    public TimeAndFormat getTimeAndFormat() {
        return timeAndFormat;
    }

    public void setTimeAndFormat(TimeAndFormat timeAndFormat) {
        this.timeAndFormat = timeAndFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
