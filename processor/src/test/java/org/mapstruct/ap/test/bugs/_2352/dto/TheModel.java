/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2352.dto;

/**
 * @author Filip Hrisafov
 */
public class TheModel {

    private final String id;

    public TheModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
