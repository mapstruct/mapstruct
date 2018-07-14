/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1247;

/**
 * @author Filip Hrisafov
 */
public class InternalDto {

    private String data2;
    private InternalData internalData;

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public InternalData getInternalData() {
        return internalData;
    }

    public void setInternalData(InternalData internalData) {
        this.internalData = internalData;
    }
}
