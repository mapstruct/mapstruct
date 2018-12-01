/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1660;

/**
 * @author Filip Hrisafov
 */
public class Source {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getStaticValue() {
        return "sourceStatic";
    }

    public String getOtherStaticValue() {
        return "sourceOtherStatic";
    }
}
