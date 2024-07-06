/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3601;

public class Source {

    private final String uuid;

    public Source(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}