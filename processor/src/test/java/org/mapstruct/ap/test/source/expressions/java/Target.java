/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java;

import org.mapstruct.ap.test.source.expressions.java.mapper.TimeAndFormat;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private TimeAndFormat timeAndFormat;
    private String anotherProp;

    public TimeAndFormat getTimeAndFormat() {
        return timeAndFormat;
    }

    public String getAnotherProp() {
        return anotherProp;
    }

    public void setAnotherProp( String anotherProp ) {
        this.anotherProp = anotherProp;
    }

    public void setTimeAndFormat(TimeAndFormat timeAndFormat) {
        this.timeAndFormat = timeAndFormat;
    }

}
