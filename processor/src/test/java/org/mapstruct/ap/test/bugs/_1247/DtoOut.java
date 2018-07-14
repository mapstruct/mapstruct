/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1247;

/**
 * @author Filip Hrisafov
 */
public class DtoOut {

    private String data;
    private InternalDto internal;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public InternalDto getInternal() {
        return internal;
    }

    public void setInternal(InternalDto internal) {
        this.internal = internal;
    }
}
