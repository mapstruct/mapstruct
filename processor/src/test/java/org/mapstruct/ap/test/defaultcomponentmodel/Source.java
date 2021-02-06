/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultcomponentmodel;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private final String id;

    public Source(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
