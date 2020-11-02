/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2263;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private final String value;

    public Target(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
