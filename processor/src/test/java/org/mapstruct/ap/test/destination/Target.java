/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

public class Target {

    private final Integer source;

    public Target(Integer source) {
        this.source = source;
    }

    public Integer getSource() {
        return source;
    }
}
