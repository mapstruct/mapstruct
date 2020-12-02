/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotations;

/**
 * @author Raimund Klein
 */
public class Target {

    private final String id;

    public Target(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
