/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1247;

/**
 * @author Filip Hrisafov
 */
public class DtoIn {

    private final String data;
    private final String data2;

    public DtoIn(String data, String data2) {
        this.data = data;
        this.data2 = data2;
    }

    public String getData() {
        return data;
    }

    public String getData2() {
        return data2;
    }
}
