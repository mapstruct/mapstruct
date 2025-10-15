/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3905;

/**
 * @author Filip Hrisafov
 */
public class OverrideDto {

    private final String name;

    public OverrideDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
