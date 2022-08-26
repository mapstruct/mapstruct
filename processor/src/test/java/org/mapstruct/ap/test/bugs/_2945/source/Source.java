/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2945.source;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private final String property;

    public Source(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
