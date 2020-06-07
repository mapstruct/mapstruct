/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2109;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private final Long id;
    private final byte[] data;

    public Target(Long id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }
}
