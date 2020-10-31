/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2251;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private final String value;

    public Source(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
