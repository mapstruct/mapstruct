/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1247;

/**
 * @author Filip Hrisafov
 */
public class OtherDtoOut {

    private String data;
    private OtherInternalDto internal;
    private String constant;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public OtherInternalDto getInternal() {
        return internal;
    }

    public void setInternal(OtherInternalDto internal) {
        this.internal = internal;
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }
}
